package com.v1.apiDebt.Io.infra.adapter;

import com.v1.apiDebt.Io.application.mappers.ContasMapper;
import com.v1.apiDebt.Io.application.ports.input.usuario.BuscarUsuarioPorIdUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.models.Contas;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.ContaNaoEncontradaException;
import com.v1.apiDebt.Io.exceptions.ErroAtualizacaoContaException;
import com.v1.apiDebt.Io.exceptions.ErroCriacaoContaException;
import com.v1.apiDebt.Io.exceptions.UsuarioNaoEncontradoException;
import com.v1.apiDebt.Io.infra.entity.ContasEntity;
import com.v1.apiDebt.Io.infra.jpaRepository.ContasJpaRepository;
import com.v1.apiDebt.Io.infra.jpaRepository.UsuarioJpaRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;
import static com.v1.apiDebt.Io.infra.util.Utils.logConsole;

@Repository
public class ContasRepositoryAdapter implements ContasRepositoryPort {
    // Injeção de Dependências
    private final ContasJpaRepository contasRepository;
    private final EntradaLogService logService;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ContasMapper contasMapper;

    public ContasRepositoryAdapter(ContasJpaRepository contasRepository, EntradaLogService logService,
                                   BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, ContasMapper contasMapper) {
        this.contasRepository = contasRepository;
        this.logService = logService;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.contasMapper = contasMapper;
    }

    @Override
    public List<Contas> criar(Contas contas) {
        try {
            logConsole.info("Iniciando rotina de criação de contas::ContasRepositoryAdapter");

            Usuario usuarioEncontrado = buscarUsuarioPorIdUseCase.buscarPorId(contas.getUsuario().getId());
            if (usuarioEncontrado == null) {
                logConsole.error("Usuário não encontrado");
                throw new UsuarioNaoEncontradoException(USR006.getMessage(), USR006.getCode());
            }

            List<Contas> contasCriadas = new ArrayList<>();

            if (contas.isContaRecorrente()) {
                logConsole.info("Conta recorrente detectada, seguindo processo de criação de contas");
                Long codigoRecorrencia = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
                for (int i = 0; i < 6; i++) {
                    Contas novaConta = new Contas(
                            contas.getCpfUsuario(),
                            contas.getNomeCompra(),
                            contas.getValor(),
                            contas.getTipoPagamento(),
                            contas.getCategoria(),
                            LocalDateTime.now(),
                            contas.getDataAtualizacao(),
                            contas.getDataVencimento().plusMonths(i),
                            usuarioEncontrado,
                            true,
                            contas.getStatusConta(),
                            codigoRecorrencia,
                            contas.getDataPagamento()
                    );
                    ContasEntity entidadeSalva = contasRepository.save(contasMapper.deDominioParaEntidade(novaConta));
                    contasCriadas.add(contasMapper.deEntidadeParaDominio(entidadeSalva));
                }
            } else {
                ContasEntity entidadeSalva = contasRepository.save(contasMapper.deDominioParaEntidade(contas));
                contasCriadas.add(contasMapper.deEntidadeParaDominio(entidadeSalva));
            }
            // Salvando log de sucesso e retornando contas criadas
            String mensagemSucesso = "Rotina de criação de contas finalizada com sucesso::ContasRepositoryAdapter";
            logService.saveLog("INFO", "ContasRepositoryAdapter", mensagemSucesso, null);
            return contasCriadas;

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro na rotina de criação de contas. Método: criar::" +
                    "ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            throw new ErroCriacaoContaException(CON001.getMessage(), CON001.getCode());
        }
    }

    @Override
    public boolean deletarConta(Long contaId) {

        try {
            if (!contasRepository.existsById(contaId)) {
                logConsole.error("Conta não encontrada::ContasRepositoryAdapter");
                throw new ContaNaoEncontradaException(CON002.getMessage(), CON002.getCode());
            }

            ContasEntity contaEntity = contasRepository.findById(contaId)
                    .orElseThrow(() -> new ContaNaoEncontradaException(CON002.getMessage(), CON002.getCode()));

            if (contaEntity.isContaRecorrente()) {
                logConsole.info("Deletando contas recorrentes::ContasRepositoryAdapter");
                List<ContasEntity> contasRecorrentes = contasRepository
                        .findAllByCodigoRecorrencia(contaEntity.getCodigoRecorrencia());
                for (ContasEntity contaRecorrente : contasRecorrentes) {
                    contasRepository.deleteById(contaRecorrente.getId());
                }
                String mensagemSucesso = "Rotina de deleção de contas recorrentes finalizada com sucesso" +
                        "::ContasRepositoryAdapter";
                logService.saveLog("INFO", "ContasRepositoryAdapter", mensagemSucesso, null);
                return true;
            } else {
                contasRepository.deleteById(contaId);
                String mensagemSucesso = "Rotina de deleção de conta finalizada com sucesso::ContasRepositoryAdapter";
                logService.saveLog("INFO", "ContasRepositoryAdapter", mensagemSucesso, null);
                return true;
            }

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro na rotina de deleção de contas. Método: deletarConta::" +
                    "ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return false;
        }
    }

    @Override
    public Contas atualizar(Contas contas) {
        try {
            // Verifica se a conta existe
            ContasEntity contaEntity = contasRepository.findById(contas.getId()).orElse(null);
            if (contaEntity == null) {
                logConsole.error("Conta não encontrada::ContasRepositoryAdapter");
                throw new ContaNaoEncontradaException(CON002.getMessage(), CON002.getCode());
            }

            // Verificando se a conta é recorrente
            if (contaEntity.isContaRecorrente()) {
                List<ContasEntity> recorrentes = contasRepository
                        .findAllByCodigoRecorrencia(contaEntity.getCodigoRecorrencia());
                for (ContasEntity recorrente : recorrentes) {
                    // Atualiza os dados da conta recorrente
                    recorrente.setNomeCompra(contas.getNomeCompra());
                    recorrente.setValor(contas.getValor());
                    recorrente.setTipoPagamento(contas.getTipoPagamento());
                    recorrente.setCategoria(contas.getCategoria());
                    recorrente.setDataAtualizacao(LocalDateTime.now());
                    recorrente.setDataVencimento(contas.getDataVencimento());
                    contasRepository.save(recorrente);
                }

                // Filtrando apenas contas futuras ou em aberto para o limite
                long contasFuturas = recorrentes.stream()
                        .filter(c -> c.getDataVencimento().isAfter(LocalDate.now()))
                        .count();

                // Verifica se a conta está paga ou atrasada e se o limite de contas futuras é menor que 6
                if ((contas.getStatusConta().equals(StatusContaEnum.PAGO) || contas.getStatusConta()
                        .equals(StatusContaEnum.ATRASADO)) && contasFuturas < 6) {

                    Contas novaConta = new Contas(
                            contas.getCpfUsuario(),
                            contas.getNomeCompra(),
                            contas.getValor(),
                            contas.getTipoPagamento(),
                            contas.getCategoria(),
                            LocalDateTime.now(),
                            null,
                            contas.getDataVencimento().plusMonths(1),
                            contas.getUsuario(),
                            true,
                            StatusContaEnum.PENDENTE,
                            contaEntity.getCodigoRecorrencia(),
                            contas.getDataPagamento()
                    );
                    ContasEntity entidadeSalva = contasRepository.save(contasMapper.deDominioParaEntidade(novaConta));
                }
                String mensagemSucesso = "Rotina de atualização de contas recorrentes finalizada com sucesso" +
                        "::ContasRepositoryAdapter";
                logService.saveLog("INFO", "ContasRepositoryAdapter", mensagemSucesso, null);
                return contasMapper.deEntidadeParaDominio(contaEntity);
            }
            // Atualiza a conta original
            contaEntity.setNomeCompra(contas.getNomeCompra());
            contaEntity.setValor(contas.getValor());
            contaEntity.setTipoPagamento(contas.getTipoPagamento());
            contaEntity.setCategoria(contas.getCategoria());
            contaEntity.setStatusConta(contas.getStatusConta());
            contaEntity.setDataAtualizacao(LocalDateTime.now());

            // Atualiza a conta original e registra o log
            ContasEntity contaSalva = contasRepository.save(contaEntity);
            String mensagemSucesso = "Rotina de atualização de contas recorrentes finalizada com sucesso" +
                    "::ContasRepositoryAdapter";
            logService.saveLog("INFO", "ContasRepositoryAdapter", mensagemSucesso, null);
            return contasMapper.deEntidadeParaDominio(contaSalva);

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro na rotina de atualização de contas. Método: atualizar::" +
                    "ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            throw new ErroAtualizacaoContaException(CON003.getMessage(), CON003.getCode());
        }
    }

    @Override
    public List<Contas> listarContasPorUsuario(UUID usuarioId) {
        try {
            List<ContasEntity> contasEntities = contasRepository.findAllByUsuarioId(usuarioId);
            if (contasEntities.isEmpty()) {
                logConsole.warn("Nenhuma conta encontrada para o usuário ID: {}", usuarioId);
                return new ArrayList<>();
            }

            LocalDate hoje = LocalDate.now();
            boolean temAlteracao = false;

            // Atualiza o status das contas atrasadas
            for (ContasEntity conta : contasEntities) {
                if (conta.getDataVencimento().isBefore(hoje)
                        && conta.getStatusConta() == StatusContaEnum.PENDENTE && conta.getDataPagamento() == null) {
                    conta.setStatusConta(StatusContaEnum.ATRASADO);
                    conta.setDataAtualizacao(LocalDateTime.now());
                    temAlteracao = true;
                }
            }

            if (temAlteracao) {
                contasRepository.saveAll(contasEntities);
                logConsole.info("Contas atualizadas com sucesso para o usuário ID: {}", usuarioId);
            }

            List<Contas> contas = contasEntities.stream()
                    .map(contasMapper::deEntidadeParaDominio)
                    .toList();

            logConsole.info("Contas listadas com sucesso para o usuário ID: {}", usuarioId);
            return contas;

        } catch (Exception ex) {
            String mensagemErro = "Erro ao listar contas por usuário. Método: listarContasPorUsuario::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Contas> buscarPorId(Long id) {
        try {
            Optional<ContasEntity> contaEntity = contasRepository.findById(id);
            return contaEntity.map(contasMapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar conta por ID. Método: buscarPorId::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contas> buscarPorNome(String nome) {
        try {
            Optional<ContasEntity> contaEntity = contasRepository.findByNomeCompra(nome);
            return contaEntity.map(contasMapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar conta por nome. Método: buscarPorNome::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contas> buscarPorCategoria(CategoriasEnum categoriasEnum) {
        try {
            Optional<ContasEntity> contaEntity = contasRepository.findByCategoria(categoriasEnum);
            return contaEntity.map(contasMapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar conta por categoria. Método: buscarPorCategoria::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contas> buscarPorDataVencimento(String dataVencimento) {
        try {
            LocalDate data = LocalDate.parse(dataVencimento);
            Optional<ContasEntity> contaEntity = contasRepository.findByDataVencimento(data);
            return contaEntity.map(contasMapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar conta por data de vencimento. Método: buscarPorDataVencimento::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contas> buscarPorDataPagamento(String dataPagamento) {
        try {
            LocalDateTime data = LocalDateTime.parse(dataPagamento);
            Optional<ContasEntity> contaEntity = contasRepository.findByDataPagamento(data);
            return contaEntity.map(contasMapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar conta por data de pagamento. Método: buscarPorDataPagamento::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contas> buscarPorUsuarioId(UUID usuarioId) {
        try {
            Optional<ContasEntity> contaEntity = contasRepository.findbyUsuarioId(usuarioId);
            return contaEntity.map(contasMapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar conta por usuário ID. Método: buscarPorUsuarioId::ContasRepositoryAdapter";
            logService.saveLog("ERROR", "ContasRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }
}
