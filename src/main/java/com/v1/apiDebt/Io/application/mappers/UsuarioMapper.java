package com.v1.apiDebt.Io.application.mappers;

import com.v1.apiDebt.Io.domain.models.FotoPerfil;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.ErroMapperException;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import com.v1.apiDebt.Io.infra.entity.UsuarioEntity;
import org.springframework.stereotype.Component;
import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;

@Component
public class UsuarioMapper {
    // Injeção de Dependência
    private final EntradaLogService logService;

    public UsuarioMapper(EntradaLogService logService) {
        this.logService = logService;
    }
    
    public UsuarioEntity deDominioParaEntidade(Usuario usuario) {
        // Verificação se o usuário é nulo
        if (usuario == null)
            return null;

        // Tentando converter para entity
        try {
            UsuarioEntity entity = new UsuarioEntity();
            entity.setId(usuario.getId());
            entity.setNome(usuario.getNome());
            entity.setSobrenome(usuario.getSobrenome());
            entity.setEmail(usuario.getEmail());
            entity.setSenha(usuario.getSenha());
            entity.setCpf(usuario.getCpf());
            entity.setTelefone(usuario.getTelefone());
            entity.setDataNascimento(usuario.getDataNascimento());
            entity.setRendaMensal(usuario.getRendaMensal());
            entity.setDataCadastro(usuario.getDataCadastro());
            entity.setDataAtualizacao(usuario.getDataAtualizacao());
            entity.setPercentualGastos(usuario.getPercentualGastos());
            entity.setFotoPerfil(usuario.getFotoPerfil());
            entity.setContas(null);
            return entity;

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro durante a execução do Mapper, método: deDominioParaEntidade" +
                    "::UsuarioMapper";
            logService.saveLog("ERROR", "UsuarioMapper", mensagemErro, ex.toString());
            throw new ErroMapperException(MAP001.getMessage(), MAP001.getCode());
        }
    }

    public Usuario deEntidadeParaDominio(UsuarioEntity entity) {
          if (entity == null)
              return null;

          // Tentando converter para domínio
          try {
              Usuario usuario = new Usuario();
              usuario.setId(entity.getId());
              usuario.setNome(entity.getNome());
              usuario.setSobrenome(entity.getSobrenome());
              usuario.setEmail(entity.getEmail());
              usuario.setSenha(entity.getSenha());
              usuario.setCpf(entity.getCpf());
              usuario.setTelefone(entity.getTelefone());
              usuario.setDataNascimento(entity.getDataNascimento());
              usuario.setRendaMensal(entity.getRendaMensal());
              usuario.setDataCadastro(entity.getDataCadastro());
              usuario.setDataAtualizacao(entity.getDataAtualizacao());
              usuario.setAtivo(entity.getAtivo());
              usuario.setPercentualGastos(entity.getPercentualGastos());
              usuario.setFotoPerfil(entity.getFotoPerfil());
              return usuario;

          } catch (Exception ex) {
              String menErro = "Ocorreu um erro na execução do Mapper, método: deEntidadeParaDominio" +
                      "::UsuarioMapper";
              logService.saveLog("ERROR", "UsuarioMapper", menErro, ex.toString());
              throw new ErroMapperException(MAP002.getMessage(), MAP002.getCode());
          }
    }
}
