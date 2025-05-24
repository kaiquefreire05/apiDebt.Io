package com.v1.apiDebt.Io.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// Validadores
import com.v1.apiDebt.Io.domain.validators.ValidadorCpf;
import com.v1.apiDebt.Io.domain.validators.ValidadorEmail;
import com.v1.apiDebt.Io.domain.validators.ValidadorSenha;
import com.v1.apiDebt.Io.domain.validators.ValidadorTelefone;

// Exceções
import com.v1.apiDebt.Io.exceptions.CpfInvalidoException;
import com.v1.apiDebt.Io.exceptions.EmailInvalidoException;
import com.v1.apiDebt.Io.exceptions.SenhaInvalidaException;
import com.v1.apiDebt.Io.exceptions.TelefoneInvalidoException;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;

public class Usuario {

    // Atributos
    private UUID id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
    private BigDecimal rendaMensal;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private List<Contas> contas;
    private Boolean ativo = false;
    private BigDecimal percentualGastos;
    private byte[] fotoPerfil;

    // Construtores
    public Usuario() {}

    public Usuario(UUID id, String nome, String sobrenome, String email, String senha, String cpf, String telefone,
                   LocalDate dataNascimento, BigDecimal rendaMensal,
                   LocalDateTime dataAtualizacao, List<Contas> contas, Boolean ativo, BigDecimal percentualGastos,
                   byte[] fotoPerfil) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        setEmail(email);
        setSenha(senha);
        setCpf(cpf);
        setTelefone(telefone);
        this.dataNascimento = dataNascimento;
        this.rendaMensal = rendaMensal;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = dataAtualizacao;
        this.contas = contas;
        this.ativo = ativo;
        this.percentualGastos = percentualGastos;
        this.fotoPerfil = fotoPerfil;
    }

    // SEM ID
    public Usuario(String nome, String sobrenome, String email, String senha, String cpf, String telefone,
                   LocalDate dataNascimento, BigDecimal rendaMensal,
                   List<Contas> contas, LocalDateTime dataAtualizacao, Boolean ativo, BigDecimal percentualGastos,
                   byte[] fotoPerfil) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        setEmail(email);
        setSenha(senha);
        setCpf(cpf);
        setTelefone(telefone);
        this.dataNascimento = dataNascimento;
        this.rendaMensal = rendaMensal;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = dataAtualizacao;
        this.contas = contas;
        this.ativo = ativo;
        this.percentualGastos = percentualGastos;
        this.fotoPerfil = fotoPerfil;
    }

    // Construtor para criar um usuário com ID
    public Usuario(UUID id) {
        this.id = id;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!ValidadorEmail.emailValido(email)) {
            throw new EmailInvalidoException(USR003.getMessage(), USR003.getCode());
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (!ValidadorSenha.validaSenha(senha)) {
            throw new SenhaInvalidaException(USR004.getMessage(), USR004.getCode());
        }
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
    String cpfLimpo = cpf.replaceAll("\\D", "");
    if (!ValidadorCpf.cpfValido(cpfLimpo)) {
        throw new CpfInvalidoException(USR001.getMessage(), USR001.getCode());
    }
    this.cpf = cpfLimpo;
}

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        // Remove caracteres especiais do telefone
        String telefoneLimpo = telefone.replaceAll("[^\\d]", "");
        if (!ValidadorTelefone.validaTelefone(telefoneLimpo)) {
            throw new TelefoneInvalidoException(USR002.getMessage(), USR002.getCode());
        }
        this.telefone = telefoneLimpo; // Armazena o telefone sem caracteres especiais
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<Contas> getContas() {
        return contas;
    }

    public void setContas(List<Contas> contas) {
        this.contas = contas;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getPercentualGastos() {
        return percentualGastos;
    }

    public void setPercentualGastos(BigDecimal percentualGastos) {
        this.percentualGastos = percentualGastos;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    // Equals e HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
