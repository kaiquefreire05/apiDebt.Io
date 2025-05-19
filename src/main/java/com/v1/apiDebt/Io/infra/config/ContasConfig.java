package com.v1.apiDebt.Io.infra.config;

import com.v1.apiDebt.Io.application.ports.input.contas.*;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.application.services.contas.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContasConfig {

    @Bean
    public AtualizarContaUseCase atualizarContaUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new AtualizarContaService(contasRepositoryPort);
    }

    @Bean
    public CriarContaUseCase criarContaUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new CriarContaService(contasRepositoryPort);
    }

    @Bean
    public DeletarContaUseCase deletarContaUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new DeletarContaService(contasRepositoryPort);
    }

    @Bean
    public ListarContasUsuarioUseCase listarContasUsuarioUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new ListarContasUsuarioService(contasRepositoryPort);
    }

    @Bean
    public BuscarContaPorIdUseCase buscarContaPorIdUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new BuscarContaPorIdService(contasRepositoryPort);
    }

    @Bean
    public AlterarStatusContaUseCase alterarStatusContaUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new AlterarStatusContaService(contasRepositoryPort);
    }

    @Bean
    public ObterTotalGastoMesUseCase obterTotalGastoMesUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new ObterTotalGastoMesService(contasRepositoryPort);
    }

    @Bean
    public SaldoRestanteUseCase saldoRestanteUseCase(ContasRepositoryPort contasRepositoryPort) {
        return new SaldoRestanteService(contasRepositoryPort);
    }

}
