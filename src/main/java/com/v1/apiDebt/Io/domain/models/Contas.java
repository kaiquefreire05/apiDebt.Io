package com.v1.apiDebt.Io.domain.models;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private Usuario usuario;

    // Construtores
    public Contas(Long id, String cpfUsuario, String nomeCompra, BigDecimal valor, TipoPagamentoEnum tipoPagamento,
                  CategoriasEnum categoria, LocalDateTime dataAtualizacao, Usuario usuario) {
        this.id = id;
        this.cpfUsuario = cpfUsuario;
        this.nomeCompra = nomeCompra;
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
        this.categoria = categoria;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = dataAtualizacao;
        this.usuario = usuario;
    }

    // Sem ID
    public Contas(String cpfUsuario, String nomeCompra, BigDecimal valor, TipoPagamentoEnum tipoPagamento,
                  CategoriasEnum categoria, LocalDateTime dataAtualizacao, Usuario usuario) {
        this.cpfUsuario = cpfUsuario;
        this.nomeCompra = nomeCompra;
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
        this.categoria = categoria;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = dataAtualizacao;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}