package com.curso.hefishing.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "fornecedor",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_fornecedor_cnpj",
                columnNames = "cnpj"))
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false, length = 150)
    private String razaoSocial;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    protected Fornecedor() {
    }

    public Fornecedor(String razaoSocial, String cnpj) {
        this.razaoSocial = validarTextoObrigatorio(razaoSocial);
        this.cnpj = validarCnpj(cnpj);
        this.status = Status.ATIVO;
    }

    private String validarTextoObrigatorio(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Razão social é obrigatória");
        }

        return valor.trim();
    }

    private String validarCnpj(String valor) {
        if (valor == null || !valor.matches("\\d{14}")) {
            throw new IllegalArgumentException(
                    "CNPJ deve possuir exatamente 14 dígitos"
            );
        }

        return valor;
    }

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Status getStatus() {
        return status;
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }
}