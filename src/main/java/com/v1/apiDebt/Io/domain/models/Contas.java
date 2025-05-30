package com.v1.apiDebt.Io.domain.models;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.enums.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Contas {

    // Atributos
    private Long id;
    private String cpfUsuario;
    private String nomeCompra;
    private BigDecimal valor;
    private TipoPagamentoEnum tipoPagamento;
    private CategoriasEnum categoria;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDate dataVencimento;
    private Usuario usuario;
    private Boolean contaRecorrente = false;
    private StatusContaEnum statusConta;
    private Long codigoRecorrencia;
    private LocalDateTime dataPagamento;

    // Construtores
    public Contas(Long id, String cpfUsuario, String nomeCompra, BigDecimal valor, TipoPagamentoEnum tipoPagamento,
                  CategoriasEnum categoria, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
                  LocalDate dataVencimento, Usuario usuario, boolean contaRecorrente,
                  StatusContaEnum statusConta, Long codigoRecorrencia, LocalDateTime dataPagamento) {
        this.id = id;
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


    // Sem ID
    public Contas(String cpfUsuario, String nomeCompra, BigDecimal valor, TipoPagamentoEnum tipoPagamento,
                  CategoriasEnum categoria, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
                  LocalDate dataVencimento, Usuario usuario, boolean contaRecorrente, StatusContaEnum statusConta,
                  Long codigoRecorrencia, LocalDateTime dataPagamento) {
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

    // Construtor vazio
    public Contas() {

    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getNomeCompra() {
        return nomeCompra;
    }

    public void setNomeCompra(String nomeCompra) {
        this.nomeCompra = nomeCompra;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoPagamentoEnum getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamentoEnum tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public CategoriasEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriasEnum categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isContaRecorrente() {
        return contaRecorrente;
    }

    public void setContaRecorrente(boolean contaRecorrente) {
        this.contaRecorrente = contaRecorrente;
    }

    public StatusContaEnum getStatusConta() {
        return statusConta;
    }

    public void setStatusConta(StatusContaEnum statusConta) {
        this.statusConta = statusConta;
    }

    public Long getCodigoRecorrencia() {
        return codigoRecorrencia;
    }

    public void setCodigoRecorrencia(Long codigoRecorrencia) {
        this.codigoRecorrencia = codigoRecorrencia;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    // Equals e HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contas contas = (Contas) o;

        return Objects.equals(id, contas.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}