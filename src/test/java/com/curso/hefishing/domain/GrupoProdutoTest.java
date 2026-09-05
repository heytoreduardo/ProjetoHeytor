package com.curso.hefishing.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GrupoProdutoTest {

    private Produto criarProduto(String codigo) {
        return new Produto(
                codigo,
                "Vara de Pesca",
                new BigDecimal("10"),
                new BigDecimal("150.00"),
                LocalDate.of(2026, 9, 5)
        );
    }

    @Test
    void deveCriarGrupoAtivo() {
        GrupoProduto grupo = new GrupoProduto("Varas de Pesca");

        assertEquals("Varas de Pesca", grupo.getNome());
        assertEquals(Status.ATIVO, grupo.getStatus());
        assertTrue(grupo.getProdutos().isEmpty());
    }

    @Test
    void deveInativarGrupo() {
        GrupoProduto grupo = new GrupoProduto("Varas de Pesca");

        grupo.inativar();

        assertEquals(Status.INATIVO, grupo.getStatus());
    }

    @Test
    void deveAtivarGrupo() {
        GrupoProduto grupo = new GrupoProduto("Varas de Pesca");

        grupo.inativar();
        grupo.ativar();

        assertEquals(Status.ATIVO, grupo.getStatus());
    }

    @Test
    void naoDeveCriarGrupoSemNome() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GrupoProduto(" ")
        );
    }

    @Test
    void deveAdicionarProdutoAoGrupo() {
        GrupoProduto grupo = new GrupoProduto("Equipamentos de Pesca");
        Produto produto = criarProduto("7891234567890");

        grupo.adicionarProduto(produto);

        assertEquals(1, grupo.getProdutos().size());
        assertSame(produto, grupo.getProdutos().get(0));
        assertSame(grupo, produto.getGrupo());
    }

    @Test
    void naoDeveAdicionarProdutosComMesmoCodigoDeBarras() {
        GrupoProduto grupo = new GrupoProduto("Equipamentos de Pesca");
        Produto produto1 = criarProduto("7891234567890");
        Produto produto2 = criarProduto("7891234567890");

        grupo.adicionarProduto(produto1);

        assertThrows(
                IllegalArgumentException.class,
                () -> grupo.adicionarProduto(produto2)
        );
    }
}
