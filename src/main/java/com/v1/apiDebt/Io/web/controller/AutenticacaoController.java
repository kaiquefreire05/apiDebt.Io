package com.v1.apiDebt.Io.web.controller;

import com.v1.apiDebt.Io.application.ports.input.usuario.AutenticarUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.input.usuario.BuscarUsuarioPorEmailUseCase;
import com.v1.apiDebt.Io.application.ports.input.usuario.ConfirmarCadastroUseCase;
import com.v1.apiDebt.Io.application.ports.input.usuario.CriarUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeEmailPort;
import com.v1.apiDebt.Io.domain.models.FotoPerfil;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.infra.adapter.EmailService;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import com.v1.apiDebt.Io.infra.security.TokenService;
import com.v1.apiDebt.Io.web.dto.request.LoginRequest;
import com.v1.apiDebt.Io.web.dto.request.RegisterRequest;
import com.v1.apiDebt.Io.web.dto.response.BaseResponse;
import com.v1.apiDebt.Io.web.dto.response.LoginResponse;
import com.v1.apiDebt.Io.web.factory.BaseResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticacaoController {
    // Injeção de Dependência
    private final TokenService tokenService;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    private final DisponibilidadeEmailPort disponibilidadeEmailPort;
    private final EntradaLogService entradaLogService;
    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final EmailService emailService;
    private final ConfirmarCadastroUseCase confirmarCadastro;

    public AutenticacaoController(TokenService tokenService, AutenticarUsuarioUseCase autenticarUsuarioUseCase,
                                  BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase,
                                  DisponibilidadeEmailPort disponibilidadeEmailPort,
                                  EntradaLogService entradaLogService, CriarUsuarioUseCase criarUsuarioUseCase,
                                  EmailService emailService, ConfirmarCadastroUseCase confirmarCadastro) {
        this.tokenService = tokenService;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.buscarUsuarioPorEmailUseCase = buscarUsuarioPorEmailUseCase;
        this.disponibilidadeEmailPort = disponibilidadeEmailPort;
        this.entradaLogService = entradaLogService;
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.emailService = emailService;
        this.confirmarCadastro = confirmarCadastro;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        if (request == null || request.email() == null || request.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Dados de login inválidos"));
        }

        try {
            boolean sucesso = autenticarUsuarioUseCase.autenticar(request.email(), request.password());
            Usuario usuario = buscarUsuarioPorEmailUseCase.buscarPorEmail(request.email()); // Gerar token / verificação ativo

            if (sucesso) {
                String token = tokenService.gerarToken(request.email(), false);

                LoginResponse loginResponse = new LoginResponse(usuario.getId(), token);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponseFactory.sucesso(loginResponse, "Usuário autenticado com sucesso"));

            } else if (!usuario.getAtivo()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseResponseFactory.falha("Usuário não está ativo. Verifique seu e-mail para ativação."));

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseResponseFactory.falha("Credenciais inválidas ou usuário não está ativo."));
            }
        } catch (Exception e) {
            String mensagemErro = "Ocorreu um erro ao tentar autenticar o usuário.";
            entradaLogService.saveLog("ERROR", "AutenticacaoController", mensagemErro, e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponseFactory.falha(e.getMessage()));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<BaseResponse<?>> registrar(@Valid @RequestBody RegisterRequest request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseFactory.falha("Requisição inválida. Verifique os dados enviados."));
        }

        // Verifica se o usuário já existe
        var existeEmail = disponibilidadeEmailPort.emailJaCadastrado(request.email());
        if (existeEmail) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(BaseResponseFactory.falha("Já existe um usuário cadastrado com esse e-mail"));
        }

        BigDecimal percentualGastos = BigDecimal.valueOf(request.percentualGastos() / 100.0);

        // Verifica se existe uma foto de perfil
        byte[] fotoBytes = null;
        if (request.fotoPerfilBase64() != null && !request.fotoPerfilBase64().isEmpty()) {
            fotoBytes = Base64.getDecoder().decode(request.fotoPerfilBase64());
        }

        // Cria um novo usuário
        Usuario novoUsuario = new Usuario(
            null,
            request.nome(),
            request.sobrenome(),
            request.email(),
            request.senha(),
            request.cpf(),
            request.telefone(),
            request.dataNascimento(),
            request.rendaMensal(),
            null,
            null,
            false,
            percentualGastos,
            fotoBytes
        );

        Usuario usuarioCriado = criarUsuarioUseCase.criar(novoUsuario);
        String token = tokenService.gerarToken(usuarioCriado.getEmail(), true);
        emailService.enviarEmailConfirmacao(usuarioCriado.getEmail(), token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.sucesso(null, "Usuário registrado com sucesso"));
    }

//    @GetMapping("/confirmar-cadastro")
//    public ResponseEntity<BaseResponse<?>> confirmarCadastro(@RequestParam("token") String token) {
//        if (token == null || token.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(BaseResponseFactory.falha("Token inválido"));
//        }
//
//        try {
//            boolean sucesso = confirmarCadastro.confirmarCadastro(token);
//            if (sucesso) {
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(BaseResponseFactory.sucesso(true, "Cadastro confirmado com sucesso"));
//            }
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(BaseResponseFactory.falha("Ocorreu um erro ao confirmar o cadastro"));
//
//        } catch (Exception ex) {
//            String mensagemErro = "Ocorreu um erro ao tentar confirmar o cadastro.";
//            entradaLogService.saveLog("ERROR", "AutenticacaoController", mensagemErro, ex.toString());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(BaseResponseFactory.falha("Erro ao processar a solicitação"));
//        }
//    }

    @GetMapping("/confirmar-cadastro")
    public ResponseEntity<Void> confirmarCadastro(@RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            boolean sucesso = confirmarCadastro.confirmarCadastro(token);

            String url = sucesso
                    ? "/pagina_confirmacao.html?token=" + token
                    : "/pagina_confirmacao.html?erro=true";

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(url));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro ao tentar confirmar o cadastro.";
            entradaLogService.saveLog("ERROR", "AutenticacaoController", mensagemErro, ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
