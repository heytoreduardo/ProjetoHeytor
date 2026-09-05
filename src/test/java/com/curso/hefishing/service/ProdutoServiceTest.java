package com.curso.hefishing.service;

import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.domain.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private GrupoProdutoService grupoProdutoService;

    @Test
    void deveCadastrarProdutoComGrupo() {
        GrupoProduto grupo = grupoProdutoService.cadastrar(
                new GrupoProduto("Iscas")
        );

        Produto produto = new Produto(
                "7891234567890",
                "Isca artificial",
                new BigDecimal("10.000"),
                new BigDecimal("39.90"),
                LocalDate.now()
        );

        Produto salvo = produtoService.cadastrar(produto, grupo.getId());

        assertNotNull(salvo.getId());
        assertEquals("7891234567890", salvo.getCodigoBarras());
        assertEquals(grupo.getId(), salvo.getGrupo().getId());
    }

    @Test
    void deveImpedirProdutoDuplicadoPorCodigoBarras() {
        GrupoProduto grupo = grupoProdutoService.cadastrar(
                new GrupoProduto("Varas")
        );

        Produto primeiro = new Produto(
                "7891111111111",
                "Vara de pesca",
                new BigDecimal("5.000"),
                new BigDecimal("199.90"),
                LocalDate.now()
        );

        produtoService.cadastrar(primeiro, grupo.getId());

        Produto segundo = new Produto(
                "7891111111111",
                "Outra vara",
                new BigDecimal("2.000"),
                new BigDecimal("299.90"),
                LocalDate.now()
        );

        assertThrows(
                RecursoDuplicadoException.class,
                () -> produtoService.cadastrar(segundo, grupo.getId())
        );
    }

    @Test
    void deveLancarExcecaoQuandoGrupoNaoExiste() {
        Produto produto = new Produto(
                "7892222222222",
                "Linha de pesca",
                new BigDecimal("20.000"),
                new BigDecimal("49.90"),
                LocalDate.now()
        );

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.cadastrar(produto, 999999L)
        );
    }
}
