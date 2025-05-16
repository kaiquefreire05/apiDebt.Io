package com.v1.apiDebt.Io.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Value("${EXPIRATION_TIME_ACCOUNT}")
    private Integer expirationConfirmacaoConta;

    public String gerarToken(String username, boolean isAccountConfirmation) {
        Instant expirationDate = isAccountConfirmation
                ? generateAccountConfirmationExpirationDate()
                : generateLoginExpirationDate();

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expirationDate)
                .withIssuer("debt.Io-Api")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("debt.Io-Api")
                    .build()
                    .verify(token)
                    .getSubject();
    }

    private Instant generateLoginExpirationDate() {
        ZonedDateTime tempoExpiracao = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(expiration);
        return tempoExpiracao.toInstant();
    }

    private Instant generateAccountConfirmationExpirationDate() {
        ZonedDateTime tempoExpiracao = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(expirationConfirmacaoConta);
        return tempoExpiracao.toInstant();
    }
}
