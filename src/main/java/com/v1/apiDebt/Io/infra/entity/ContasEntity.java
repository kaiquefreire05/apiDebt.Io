package com.v1.apiDebt.Io.infra.entity;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.enums.TipoPagamentoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contas")
public class ContasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull(message = "O CPF não pode ser nulo.")
    @Column(name = "cpf_usuario", nullable = false, length = 11, unique = true)
    private String cpfUsuario;

    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 5, max = 150, message = "O nome deve ter entre 3 e 150 caracteres.")
    @Column(name = "nome_compra", nullable = false, length = 150)
    private String nomeCompra;

    @NotNull(message = "O valor não pode ser nulo.")
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotEmpty(message = "O tipo de pagamento não pode ser vazio.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento", nullable = false)
    private TipoPagamentoEnum tipoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 100)
    private CategoriasEnum categoria;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "conta_recorrente", nullable = false)
    private boolean contaRecorrente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_conta", nullable = false, length = 100)
    private StatusContaEnum statusConta;

    @Column(name = "codigo_recorrencia")
    private Long codigoRecorrencia;

    @Column(name = "data_pagamento", nullable = true)
    private LocalDateTime dataPagamento;

    // Construtor sem ID
    public ContasEntity(String cpfUsuario, String nomeCompra, BigDecimal valor, TipoPagamentoEnum tipoPagamento,
                        CategoriasEnum categoria, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
                        LocalDate dataVencimento, UsuarioEntity usuario, boolean contaRecorrente,
                        StatusContaEnum statusConta, Long codigoRecorrencia, LocalDateTime dataPagamento) {
        this.cpfUsuario = cpfUsuario;
        this.nomeCompra = nomeCompra;
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.dataVencimento = dataVencimento;
        this.usuario = usuario;
        this.contaRecorrente = contaRecorrente;
        this.statusConta = statusConta;
        this.codigoRecorrencia = codigoRecorrencia;
        this.dataPagamento = dataPagamento;
    }
}
