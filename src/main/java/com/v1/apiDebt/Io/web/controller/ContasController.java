package com.v1.apiDebt.Io.web.controller;

import com.v1.apiDebt.Io.application.ports.input.contas.*;
import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.models.Contas;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import com.v1.apiDebt.Io.web.dto.request.AtualizarContaRequest;
import com.v1.apiDebt.Io.web.dto.request.CriarContaRequest;
import com.v1.apiDebt.Io.web.dto.response.BaseResponse;
import com.v1.apiDebt.Io.web.dto.response.ContasResponse;
import com.v1.apiDebt.Io.web.factory.BaseResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contas")
public class ContasController {
    // Injeção de Dependência
    private final AtualizarContaUseCase atualizarContaUseCase;
    private final CriarContaUseCase criarContaUseCase;
    private final DeletarContaUseCase deletarContaUseCase;
    private final ListarContasUsuarioUseCase listarContasUsuarioUseCase;
    private final BuscarContaPorIdUseCase buscarContaPorIdUseCase;
    private final EntradaLogService logService;
    private final AlterarStatusContaUseCase alterarStatusContaUseCase;
    private final ObterTotalGastoMesUseCase obterTotalGastoMesUseCase;
    private final SaldoRestanteUseCase saldoRestanteUseCase;

    public ContasController(AtualizarContaUseCase atualizarContaUseCase, CriarContaUseCase criarContaUseCase,
                            DeletarContaUseCase deletarContaUseCase,
                            ListarContasUsuarioUseCase listarContasUsuarioUseCase,
                            BuscarContaPorIdUseCase buscarContaPorIdUseCase,
                            EntradaLogService logService,
                            AlterarStatusContaUseCase alterarStatusContaUseCase,
                            ObterTotalGastoMesUseCase obterTotalGastoMesUseCase,
                            SaldoRestanteUseCase saldoRestanteUseCase) {
        this.atualizarContaUseCase = atualizarContaUseCase;
        this.criarContaUseCase = criarContaUseCase;
        this.deletarContaUseCase = deletarContaUseCase;
        this.listarContasUsuarioUseCase = listarContasUsuarioUseCase;
        this.buscarContaPorIdUseCase = buscarContaPorIdUseCase;
        this.logService = logService;
        this.alterarStatusContaUseCase = alterarStatusContaUseCase;
        this.obterTotalGastoMesUseCase = obterTotalGastoMesUseCase;
        this.saldoRestanteUseCase = saldoRestanteUseCase;
    }

    @PostMapping("/criar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<ContasResponse>>> criarConta(@RequestBody @Valid CriarContaRequest request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição inválida. Verifique os dados enviados."));
        }

        // Criando nova conta
        Contas novaConta = new Contas(
                null,
                request.nomeCompra(),
                request.valor(),
                request.tipoPagamento(),
                request.categoria(),
                LocalDateTime.now(),
                null,
                request.dataVencimento(),
                new Usuario(request.usuarioId()),
                request.contaRecorrente(),
                StatusContaEnum.PENDENTE,
                null,
                null
        );

        // Persistindo a nova conta, salvando log e retornando a resposta
        List<Contas> contaCriada = criarContaUseCase.criar(novaConta);
        List<ContasResponse> responses = contaCriada.stream()
                .map(conta -> new ContasResponse(
                        conta.getNomeCompra(),
                        conta.getValor(),
                        conta.getTipoPagamento(),
                        conta.getCategoria(),
                        conta.getDataVencimento(),
                        conta.getDataPagamento(),
                        conta.isContaRecorrente(),
                        conta.getStatusConta(),
                        conta.getDataCriacao(),
                        conta.getDataAtualizacao()
                ))
                .toList();
        String mensagemErro = "Conta criada com sucesso. Método: criarConta::ContasController";
        logService.saveLog("INFO", "ContasController", mensagemErro, null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.sucesso(responses, "Conta criada com sucesso"));

    }

    @PutMapping("/atualizar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<ContasResponse>> atualizarConta(@RequestBody @Valid AtualizarContaRequest request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição inválida. Verifique os dados enviados."));
        }

        // Verificando se a conta existe
        Contas contaExistente = buscarContaPorIdUseCase.buscarPorId(request.id());
        if (contaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponseFactory.falha("Conta não encontrada."));
        }

        // Atualizando os dados da conta
        contaExistente.setNomeCompra(request.nomeCompra());
        contaExistente.setValor(request.valor());
        contaExistente.setTipoPagamento(request.tipoPagamento());
        contaExistente.setCategoria(request.categoria());
        contaExistente.setDataVencimento(LocalDate.parse(request.dataVencimento()));
        contaExistente.setContaRecorrente(request.contaRecorrente());
        contaExistente.setDataAtualizacao(LocalDateTime.now());

        // Persistindo as alterações
        Contas contaAtualizada = atualizarContaUseCase.atualizar(contaExistente);
        ContasResponse response = new ContasResponse(
                contaAtualizada.getNomeCompra(),
                contaAtualizada.getValor(),
                contaAtualizada.getTipoPagamento(),
                contaAtualizada.getCategoria(),
                contaAtualizada.getDataVencimento(),
                contaAtualizada.getDataPagamento(),
                contaAtualizada.isContaRecorrente(),
                contaAtualizada.getStatusConta(),
                contaExistente.getDataCriacao(),
                contaAtualizada.getDataAtualizacao()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.sucesso(response, "Conta atualizada com sucesso."));
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<Boolean>> deletarConta(@PathVariable Long id) {
        if (id == null || id.toString().isEmpty()) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("ID inválido. Verifique o ID enviado."));
        }

        try {
            boolean deletado = deletarContaUseCase.deletarConta(id);
            if (deletado) {
                String mensagemErro = "Conta deletada com sucesso. Método: deletarConta::ContasController";
                logService.saveLog("INFO", "ContasController", mensagemErro, null);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponseFactory.sucesso(true, "Conta deletada com sucesso."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseFactory.falha("Conta não encontrada."));
            }
        } catch (Exception ex) {
            String mensagemErro = "Erro ao deletar conta. Método: deletarConta::ContasController";
            logService.saveLog("ERROR", "ContasController", mensagemErro, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));
        }
    }

    @GetMapping("listar/usuario/{usuarioId}")
    public ResponseEntity<BaseResponse<List<ContasResponse>>> listarContasPorUsuario(@PathVariable UUID usuarioId) {
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("ID de usuário inválido. Verifique o ID enviado."));
        }

        try {
            List<Contas> contas = listarContasUsuarioUseCase.listarContasUsuario(usuarioId);
            List<ContasResponse> responses = contas.stream()
                    .map(conta -> new ContasResponse(
                            conta.getNomeCompra(),
                            conta.getValor(),
                            conta.getTipoPagamento(),
                            conta.getCategoria(),
                            conta.getDataVencimento(),
                            conta.getDataPagamento(),
                            conta.isContaRecorrente(),
                            conta.getStatusConta(),
                            conta.getDataCriacao(),
                            conta.getDataAtualizacao()
                    ))
                    .toList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(responses, "Contas listadas com sucesso."));

        } catch (Exception ex) {
            String mensagemErro = "Erro ao listar contas. Método: listarContasPorUsuario::ContasController";
            logService.saveLog("ERROR", "ContasController", mensagemErro, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));
        }
    }

    @PutMapping("alterar-status/{id}/{status}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<ContasResponse>> alterarStatusConta(@PathVariable Long id,
                                                                           @PathVariable StatusContaEnum status) {
        if (id == null || id.toString().isEmpty() || status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Dados da requisição inválidos. Verifique os dados enviados."));
        }

        try {
            Contas conta = buscarContaPorIdUseCase.buscarPorId(id);
            if (conta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseFactory.falha("Conta não encontrada."));
            }

            // Alterando o status da conta
            Contas contaAtualizada = alterarStatusContaUseCase.alterarStatusConta(id, status);

            ContasResponse response = new ContasResponse(
                    contaAtualizada.getNomeCompra(),
                    contaAtualizada.getValor(),
                    contaAtualizada.getTipoPagamento(),
                    contaAtualizada.getCategoria(),
                    contaAtualizada.getDataVencimento(),
                    contaAtualizada.getDataPagamento(),
                    contaAtualizada.isContaRecorrente(),
                    contaAtualizada.getStatusConta(),
                    contaAtualizada.getDataCriacao(),
                    contaAtualizada.getDataAtualizacao()
            );

            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(response, "Status da conta alterado com sucesso."));
        } catch (Exception ex) {
            String mensagemErro = "Erro ao alterar status conta. Método: alterarStatusConta::ContasController";
            logService.saveLog("ERROR", "ContasController", mensagemErro, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));
        }
    }

    @GetMapping("total-gasto-mes/{usuarioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<BigDecimal>> obterTotalGastoMes(@PathVariable UUID usuarioId) {
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("ID de usuário inválido. Verifique o ID enviado."));
        }

        try {
            BigDecimal totalGasto = obterTotalGastoMesUseCase.obterTotalGastoMes(usuarioId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(totalGasto, "Total gasto no mês obtido com sucesso."));
        } catch (Exception ex) {
            String mensagemErro = "Erro ao obter total gasto no mês. Método: obterTotalGastoMes::ContasController";
            logService.saveLog("ERROR", "ContasController", mensagemErro, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));
        }
    }

    @GetMapping("saldo-disponivel/{usuarioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<BigDecimal>> obterSaldoRestante(@PathVariable UUID usuarioId) {
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("ID de usuário inválido. Verifique o ID enviado."));
        }

        try {
            BigDecimal saldoRestante = saldoRestanteUseCase.somarGasto(usuarioId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(saldoRestante, "Saldo restante obtido com sucesso."));
        } catch (Exception ex) {
            String mensagemErro = "Erro ao obter saldo restante. Método: obterSaldoRestante::ContasController";
            logService.saveLog("ERROR", "ContasController", mensagemErro, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));
        }
    }
}
