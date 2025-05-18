package com.v1.apiDebt.Io.application.mappers;

import com.v1.apiDebt.Io.domain.models.Contas;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.ErroMapperException;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import com.v1.apiDebt.Io.infra.entity.ContasEntity;
import com.v1.apiDebt.Io.infra.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;

@Component
public class ContasMapper {
    // Injeção de Dependência
    private final EntradaLogService logService;

    public ContasMapper(EntradaLogService logService) {
        this.logService = logService;
    }

    public ContasEntity deDominioParaEntidade(Contas conta) {
        if (conta == null) {
            return null;
        }

        try {
            ContasEntity entity = new ContasEntity();
            entity.setId(conta.getId());
            entity.setCpfUsuario(conta.getCpfUsuario());
            entity.setNomeCompra(conta.getNomeCompra());
            entity.setValor(conta.getValor());
            entity.setTipoPagamento(conta.getTipoPagamento());
            entity.setCategoria(conta.getCategoria());
            entity.setDataCriacao(conta.getDataCriacao());
            entity.setDataAtualizacao(conta.getDataAtualizacao());
            entity.setDataVencimento(conta.getDataVencimento());
            entity.setContaRecorrente(conta.isContaRecorrente());
            entity.setStatusConta(conta.getStatusConta());
            entity.setCodigoRecorrencia(conta.getCodigoRecorrencia());

            // Converter usuário de domínio para entidade
            if (conta.getUsuario() != null) {
                UsuarioEntity usuarioEntity = new UsuarioEntity();
                usuarioEntity.setId(conta.getUsuario().getId());
                entity.setUsuario(usuarioEntity);
            }

            return entity;
        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro durante a execução do Mapper, método: deDominioParaEntidade"
                    + "::ContasMapper";
            logService.saveLog("ERROR", "ContasMapper", mensagemErro, ex.toString());
            throw new ErroMapperException(MAP003.getMessage(), MAP003.getCode());
        }
    }

    public Contas deEntidadeParaDominio(ContasEntity entity) {
        if (entity == null) {
            return null;
        }

        try {
            Contas conta = new Contas();
            conta.setId(entity.getId());
            conta.setCpfUsuario(entity.getCpfUsuario());
            conta.setNomeCompra(entity.getNomeCompra());
            conta.setValor(entity.getValor());
            conta.setTipoPagamento(entity.getTipoPagamento());
            conta.setCategoria(entity.getCategoria());
            conta.setDataCriacao(entity.getDataCriacao());
            conta.setDataAtualizacao(entity.getDataAtualizacao());
            conta.setDataVencimento(entity.getDataVencimento());
            conta.setContaRecorrente(entity.isContaRecorrente());
            conta.setStatusConta(entity.getStatusConta());
            conta.setCodigoRecorrencia(entity.getCodigoRecorrencia());

            // Converter UsuarioEntity para UsuarioModel
            if (entity.getUsuario() != null) {
                Usuario usuario = new Usuario();
                usuario.setId(entity.getUsuario().getId());
                // adicione mais campos se necessário
                conta.setUsuario(usuario);
            }

            return conta;
        } catch (Exception ex) {
            String mensagemErro = "Erro ao executar o mapper deEntidadeParaDominio :: ContasMapper";
            logService.saveLog("ERROR", "ContasMapper", mensagemErro, ex.toString());
            throw new ErroMapperException(MAP004.getMessage(), MAP004.getCode()); // MAP004 deve ser mapeado em ErrorCodeEnum
        }
    }
}
