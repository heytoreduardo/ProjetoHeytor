package com.curso.hefishing.service;

import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GrupoProdutoServiceTest {

    @Autowired
    private GrupoProdutoService grupoProdutoService;

    @Test
    void deveCadastrarGrupoProduto() {
        GrupoProduto grupo = new GrupoProduto("Iscas");

        GrupoProduto salvo = grupoProdutoService.cadastrar(grupo);

        assertNotNull(salvo.getId());
        assertEquals("Iscas", salvo.getNome());
        assertEquals(Status.ATIVO, salvo.getStatus());
    }

    @Test
    void deveImpedirGrupoProdutoDuplicadoPorNome() {
        grupoProdutoService.cadastrar(new GrupoProduto("Iscas"));

        assertThrows(
                RecursoDuplicadoException.class,
                () -> grupoProdutoService.cadastrar(new GrupoProduto("iscas"))
        );
    }

    @Test
    void deveBuscarGrupoProdutoPorId() {
        GrupoProduto salvo =
                grupoProdutoService.cadastrar(new GrupoProduto("Varas"));

        GrupoProduto encontrado =
                grupoProdutoService.buscarPorId(salvo.getId());

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Varas", encontrado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarGrupoInexistente() {
        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> grupoProdutoService.buscarPorId(999999L)
        );
    }

    @Test
    void deveListarGruposProduto() {
        grupoProdutoService.cadastrar(new GrupoProduto("Anzóis"));
        grupoProdutoService.cadastrar(new GrupoProduto("Linhas"));

        assertTrue(grupoProdutoService.listar().size() >= 2);
    }
}
