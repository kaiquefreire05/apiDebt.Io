package com.v1.apiDebt.Io.infra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @NotEmpty(message = "O sobrenome não pode ser vazio.")
    @Size(min = 3, max = 150, message = "O sobrenome deve ter entre 3 e 150 caracteres.")
    @Column(name = "sobrenome", nullable = false, length = 150)
    private String sobrenome;

    @NotEmpty(message = "O email não pode ser vazio.")
    @Email(message = "Email inválido.")
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @NotEmpty(message = "A senha não pode ser vazia.")
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @NotNull(message = "O CPF não pode ser nulo.")
    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @NotEmpty(message = "O telefone não pode ser vazio.")
    @Column(name = "telefone", nullable = false, length = 15)
    private String telefone;

    @NotNull(message = "A data de nascimento não pode ser nula.")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "A renda mensal não pode ser nula.")
    @Positive(message = "A renda mensal deve ser um valor positivo.")
    @Column(name = "renda_mensal", nullable = false, precision = 10, scale = 2)
    private BigDecimal rendaMensal;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = false;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContasEntity> contas = new ArrayList<>();

    // Construtor sem ID
    public UsuarioEntity(String nome, String sobrenome, String email, String senha, String cpf, String telefone,
                         LocalDate dataNascimento, BigDecimal rendaMensal, LocalDateTime dataCadastro,
                         LocalDateTime dataAtualizacao, Boolean ativo, List<ContasEntity> contas) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.rendaMensal = rendaMensal;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.ativo = ativo;
        this.contas = contas;
    }
}
