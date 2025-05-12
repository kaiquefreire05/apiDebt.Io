package com.v1.apiDebt.Io.web.controller;

import com.v1.apiDebt.Io.application.ports.input.usuario.*;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import com.v1.apiDebt.Io.web.dto.request.AlterarSenhaRequest;
import com.v1.apiDebt.Io.web.dto.request.AtualizarUsuarioRequest;
import com.v1.apiDebt.Io.web.dto.response.BaseResponse;
import com.v1.apiDebt.Io.web.dto.response.UsuarioResponse;
import com.v1.apiDebt.Io.web.factory.BaseResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    // Injeção de Dependências
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final AlterarSenhaUseCase alterarSenhaUseCase;
    private final EntradaLogService entradaLogService;

    public UsuarioController(AtualizarUsuarioUseCase atualizarUsuarioUseCase,
                             BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase,
                             BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
                             DeletarUsuarioUseCase deletarUsuarioUseCase,
                             ListarUsuariosUseCase listarUsuariosUseCase,
                             AlterarSenhaUseCase alterarSenhaUseCase,
                             EntradaLogService entradaLogService) {
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.buscarUsuarioPorEmailUseCase = buscarUsuarioPorEmailUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.alterarSenhaUseCase = alterarSenhaUseCase;
        this.entradaLogService = entradaLogService;
    }

    @GetMapping("/todos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<UsuarioResponse>>> listarTodosUsuarios() {
        try {
            // Listando todos os usuários e transformando em response para encapsular os dados
            List<Usuario> usuarios = listarUsuariosUseCase.listarTodosUsuarios();
            List<UsuarioResponse> responses = usuarios.stream()
                    .map(u -> new UsuarioResponse(
                            u.getId(),
                            u.getNome(),
                            u.getSobrenome(),
                            u.getEmail(),
                            u.getCpf(),
                            u.getTelefone(),
                            u.getDataNascimento(),
                            u.getRendaMensal(),
                            u.getDataCadastro()
                    ))
            .toList();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(responses, "Usuarios listados com sucesso"));

        } catch (Exception ex) {
            String msg = "Ocorreu um erro ao buscar usuario";
            entradaLogService.saveLog("ERROR", "UsuarioController", msg, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));


        }
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<UsuarioResponse>> obterUsuarioPorEmail(@PathVariable String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição invalida, Verifique o e-mail enviado."));
        }

        try {
            Usuario usuario = buscarUsuarioPorEmailUseCase.buscarPorEmail(email);
            UsuarioResponse response = new UsuarioResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getSobrenome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getTelefone(),
                    usuario.getDataNascimento(),
                    usuario.getRendaMensal(),
                    usuario.getDataCadastro()
            );
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(response, "Usuario listado com sucesso"));

        }   catch (Exception ex) {
            String msg = "Ocorreu um erro ao buscar usuario";
            entradaLogService.saveLog("ERROR", "UsuarioController", msg, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));

        }
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<UsuarioResponse>> obterUsuarioPorId(@PathVariable UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição invalida, Verifique o ID enviado."));
        }

        try {
            Usuario usuario = buscarUsuarioPorIdUseCase.buscarPorId(id);
            UsuarioResponse response = new UsuarioResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getSobrenome(),
                    usuario.getEmail(),
                    usuario.getCpf(),
                    usuario.getTelefone(),
                    usuario.getDataNascimento(),
                    usuario.getRendaMensal(),
                    usuario.getDataCadastro()
            );
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponseFactory.sucesso(response, "Usuario listado com sucesso"));

        }   catch (Exception ex) {
            String msg = "Ocorreu um erro ao buscar usuario";
            entradaLogService.saveLog("ERROR", "UsuarioController", msg, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));

        }
    }

    @PutMapping("/atualizar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<UsuarioResponse>> atualizarUsuario(@Valid @RequestBody AtualizarUsuarioRequest request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição inválida. Verifique os dados enviados."));
        }

        // Verifica se o usuário já existe
        Usuario usuarioExistente = buscarUsuarioPorIdUseCase.buscarPorId(request.id());
        if (usuarioExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponseFactory.falha("Usuário não encontrado"));
        }

        // Atualiza os dados do usuário
        usuarioExistente.setNome(request.nome());
        usuarioExistente.setSobrenome(request.sobrenome());
        usuarioExistente.setEmail(request.email());
        usuarioExistente.setCpf(request.cpf());
        usuarioExistente.setTelefone(request.telefone());
        usuarioExistente.setDataAtualizacao(LocalDateTime.now());
        Usuario usuarioSalvo = atualizarUsuarioUseCase.atualizar(usuarioExistente);
        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getSobrenome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getCpf(),
                usuarioSalvo.getTelefone(),
                usuarioSalvo.getDataNascimento(),
                usuarioSalvo.getRendaMensal(),
                usuarioSalvo.getDataCadastro()
        );

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseFactory.sucesso(usuarioResponse,
                "Usuário atualizado com sucesso"));
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<Boolean>> deletarUsuario(@PathVariable UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição inválida. Verifique o ID enviado."));
        }

        try {
            boolean deletado = deletarUsuarioUseCase.deletarUsuario(id);
            if (deletado) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponseFactory.sucesso(true, "Usuário deletado com sucesso"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseFactory.falha("Usuário não encontrado"));
            }
        } catch (Exception ex) {
            String msg = "Ocorreu um erro ao deletar usuario";
            entradaLogService.saveLog("ERROR", "UsuarioController", msg, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha("Ocorreu um erro ao processar a solicitação."));
        }
    }

    @PutMapping("/alterar-senha")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<Boolean>> alterarSenha(@Valid @RequestBody AlterarSenhaRequest request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição inválida. Verifique os dados enviados."));
        }

        try {
            // Verifica se o usuário já existe
            Usuario usuarioExistente = buscarUsuarioPorIdUseCase.buscarPorId(request.id());
            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseFactory.falha("Usuário não encontrado"));
            }

            // Alterando senha do usuário
            alterarSenhaUseCase.alterarSenha(request.id(), request.senhaAtual(),
                    request.novaSenha(), request.confirmarSenha());

            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseFactory.sucesso(true,
                    "Senha alterada com sucesso"));

        } catch (Exception ex) {
            // Salvando log de erro
            String msg = "Ocorreu um erro ao deletar usuario";
            entradaLogService.saveLog("ERROR", "UsuarioController", msg, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(ex.getMessage()));
        }

    }
}