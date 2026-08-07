package io.lucascarvalho_oliveira.FinancialManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.lucascarvalho_oliveira.FinancialManagement.model.enums.TipoConta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private Integer id;
    @NotBlank
    private String nome;
    @Positive
    private BigDecimal valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate data;
    @NotBlank
    private String mes;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    protected Conta(){}

    public Conta(String nome, BigDecimal valor, LocalDate data, String mes, TipoConta tipoConta, Pessoa pessoa) {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.mes = mes;
        this.tipoConta = tipoConta;
        this.pessoa = pessoa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}