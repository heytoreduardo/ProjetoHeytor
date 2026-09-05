package com.curso.hefishing.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "produto",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_produto_codigo_barras",
                columnNames = "codigo_barras"
        )
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private final String codigoBarras;

    @Column(nullable = false, length = 150)
    private String descricao;

    @Column(nullable = false, precision = 18, scale = 3)
    private BigDecimal saldoEstoque;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorUnitario;

    @Column(
            name = "estoque_minimo",
            nullable = false,
            precision = 18,
            scale = 3
    )
    private BigDecimal estoqueMinimo;

    @Column(nullable = false)
    private final LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "grupo_produto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_produto_grupo_produto")
    )
    private GrupoProduto grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fornecedor_id",
            foreignKey = @ForeignKey(name = "fk_produto_fornecedor")
    )
    private Fornecedor fornecedor;

    protected Produto() {
        this.codigoBarras = null;
        this.dataCadastro = null;
    }

    public Produto(
            String codigoBarras,
            String descricao,
            BigDecimal saldoEstoque,
            BigDecimal valorUnitario,
            LocalDate dataCadastro) {
        this(
                codigoBarras,
                descricao,
                saldoEstoque,
                valorUnitario,
                BigDecimal.ZERO,
                dataCadastro
        );
    }

    public Produto(
            String codigoBarras,
            String descricao,
            BigDecimal saldoEstoque,
            BigDecimal valorUnitario,
            BigDecimal estoqueMinimo,
            LocalDate dataCadastro) {
        this.codigoBarras = validarTextoObrigatorio(
                codigoBarras,
                "Codigo de barras e obrigatorio");
        this.descricao = validarTextoObrigatorio(
                descricao,
                "Descricao e obrigatoria");
        this.saldoEstoque = validarNaoNegativo(
                saldoEstoque,
                "Saldo de estoque nao pode ser negativo");
        this.valorUnitario = validarNaoNegativo(
                valorUnitario,
                "Valor unitario nao pode ser negativo");
        this.estoqueMinimo = validarNaoNegativo(
                estoqueMinimo,
                "Estoque minimo nao pode ser negativo");
        this.dataCadastro = Objects.requireNonNull(
                dataCadastro,
                "Data de cadastro e obrigatoria");
        this.status = Status.ATIVO;
    }

    public BigDecimal calcularValorEstoque() {
        return saldoEstoque
                .multiply(valorUnitario)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void receberEstoque(BigDecimal quantidade) {
        validarPositivo(quantidade, "Quantidade recebida deve ser maior que zero");
        this.saldoEstoque = saldoEstoque.add(quantidade);
    }

    public void retirarEstoque(BigDecimal quantidade) {
        validarPositivo(quantidade, "Quantidade retirada deve ser maior que zero");

        if (saldoEstoque.compareTo(quantidade) < 0) {
            throw new IllegalArgumentException("Saldo de estoque insuficiente");
        }

        this.saldoEstoque = saldoEstoque.subtract(quantidade);
    }

    public void alterarDescricao(String novaDescricao) {
        this.descricao = validarTextoObrigatorio(
                novaDescricao,
                "Descricao e obrigatoria");
    }

    public void alterarValorUnitario(BigDecimal novoValor) {
        this.valorUnitario = validarNaoNegativo(
                novoValor,
                "Valor unitario nao pode ser negativo");
    }

    public void ativar() {
        this.status = Status.ATIVO;
    }

    public void inativar() {
        this.status = Status.INATIVO;
    }

    void associarAo(GrupoProduto grupo) {
        Objects.requireNonNull(grupo, "Grupo de produto e obrigatorio");

        if (this.grupo != null && this.grupo != grupo) {
            throw new IllegalStateException("Produto ja pertence a outro grupo");
        }

        this.grupo = grupo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getSaldoEstoque() {
        return saldoEstoque;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Status getStatus() {
        return status;
    }

    public GrupoProduto getGrupo() {
        return grupo;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void associarFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    private static String validarTextoObrigatorio(String texto, String mensagem) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return texto.trim();
    }

    private static BigDecimal validarNaoNegativo(BigDecimal valor, String mensagem) {
        Objects.requireNonNull(valor, mensagem);
        if (valor.signum() < 0) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor;
    }

    private static void validarPositivo(BigDecimal valor, String mensagem) {
        Objects.requireNonNull(valor, mensagem);
        if (valor.signum() <= 0) {
            throw new IllegalArgumentException(mensagem);
        }
    }
}