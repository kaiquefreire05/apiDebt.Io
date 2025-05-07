package com.v1.apiDebt.Io.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class TokenService {
    /**
     * Essa classe é responsável para gerar e validar tokens JWT.
     * Ela utiliza a biblioteca auth0 para criar e verificar os tokens.
     */

    @Value("${secret-key}")
    private String secret;

    @Value("${expiration-time}")
    private Integer expiration;

    private final EntradaLogService logService;

    public TokenService(EntradaLogService logService) {
        this.logService = logService;
    }

    public String gerarToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(generateExpirationDate())
                .withIssuer("debt.Io-Api")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("debt.Io-Api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            logService.saveLog("ERROR", "TokenService", "Erro ao validar token", ex.getMessage());
            return null;
        }
    }

    private Instant generateExpirationDate() {
        ZonedDateTime tempoExpiracao = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(expiration);
        logService.saveLog("INFO", "TokenService", "Valor de tempo de expiração do token: "
                + tempoExpiracao, null);
        return tempoExpiracao.toInstant();
    }
}
