package com.v1.apiDebt.Io.infra.security;

import com.v1.apiDebt.Io.application.ports.input.usuario.BuscarUsuarioPorEmailUseCase;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {
    /**
     * Essa classe é responsável por carregar os dados do usuário.
     * Ela implementa a interface UserDetailsService do Spring Security.
     * Os métodos loadUserByUsername é usado para recuperar os detalhes do usuário pelo email.
     */

    // Dependency Injection
    private final BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;

    public CustomUserDetailService(BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase) {
        this.buscarUsuarioPorEmailUseCase = buscarUsuarioPorEmailUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var usuario = buscarUsuarioPorEmailUseCase.buscarPorEmail(username);
            return new org.springframework.security.core.userdetails.User(
                    usuario.getEmail(),
                    usuario.getSenha(),
                    Set.of(new SimpleGrantedAuthority("ROLE_USER")) // Adiciona uma role padrão
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username, e);
        }
    }
}
