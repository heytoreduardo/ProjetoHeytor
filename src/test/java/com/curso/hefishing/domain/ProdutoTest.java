package com.curso.hefishing.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    private Produto criarProduto() {
        return new Produto(
                "7891234567890",
                "Vara de Pesca",
                new BigDecimal("10"),
                new BigDecimal("150.00"),
                LocalDate.of(2026, 9, 5)
        );
    }

    @Test
    void deveCriarProdutoAtivo() {
        Produto produto = criarProduto();

        assertEquals("7891234567890", produto.getCodigoBarras());
        assertEquals("Vara de Pesca", produto.getDescricao());
        assertEquals(new BigDecimal("10"), produto.getSaldoEstoque());
        assertEquals(new BigDecimal("150.00"), produto.getValorUnitario());
        assertEquals(LocalDate.of(2026, 9, 5), produto.getDataCadastro());
        assertEquals(Status.ATIVO, produto.getStatus());
        assertNull(produto.getGrupo());
    }

    @Test
    void deveCalcularValorDoEstoque() {
        Produto produto = criarProduto();

        BigDecimal resultado = produto.calcularValorEstoque();

        assertEquals(new BigDecimal("1500.00"), resultado);
    }

    @Test
    void deveReceberEstoque() {
        Produto produto = criarProduto();

        produto.receberEstoque(new BigDecimal("5"));

        assertEquals(new BigDecimal("15"), produto.getSaldoEstoque());
    }

    @Test
    void deveRetirarEstoque() {
        Produto produto = criarProduto();

        produto.retirarEstoque(new BigDecimal("3"));

        assertEquals(new BigDecimal("7"), produto.getSaldoEstoque());
    }

    @Test
    void naoDeveRetirarMaisEstoqueDoQuePossui() {
        Produto produto = criarProduto();

        assertThrows(
                IllegalArgumentException.class,
                () -> produto.retirarEstoque(new BigDecimal("11"))
        );
    }

    @Test
    void deveAlterarDescricaoEValorUnitario() {
        Produto produto = criarProduto();

        produto.alterarDescricao("Molinete de Pesca");
        produto.alterarValorUnitario(new BigDecimal("220.00"));

        assertEquals("Molinete de Pesca", produto.getDescricao());
        assertEquals(new BigDecimal("220.00"), produto.getValorUnitario());
    }

    @Test
    void deveAssociarProdutoAoGrupo() {
        Produto produto = criarProduto();
        GrupoProduto grupo = new GrupoProduto("Equipamentos de Pesca");

        grupo.adicionarProduto(produto);

        assertSame(grupo, produto.getGrupo());
        assertTrue(grupo.getProdutos().contains(produto));
    }
}
