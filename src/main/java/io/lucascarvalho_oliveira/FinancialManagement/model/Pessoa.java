package io.lucascarvalho_oliveira.FinancialManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;

    @OneToMany(mappedBy = "pessoa")
    List<Conta> contas;

    protected Pessoa(){}

    public Pessoa(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;

        this.contas = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
}
