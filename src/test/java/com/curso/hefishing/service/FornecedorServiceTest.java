package com.curso.hefishing.service;

import com.curso.hefishing.domain.Fornecedor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FornecedorServiceTest {

    @Autowired
    private FornecedorService fornecedorService;

    @Test
    void deveCadastrarFornecedor() {
        Fornecedor fornecedor = new Fornecedor(
                "Fornecedor Pesca Brasil",
                "12345678000199"
        );

        Fornecedor salvo = fornecedorService.cadastrar(fornecedor);

        assertNotNull(salvo.getId());
        assertEquals("Fornecedor Pesca Brasil", salvo.getRazaoSocial());
        assertEquals("12345678000199", salvo.getCnpj());
    }

    @Test
    void deveImpedirFornecedorDuplicadoPorCnpj() {
        fornecedorService.cadastrar(
                new Fornecedor(
                        "Fornecedor Pesca Brasil",
                        "12345678000199"
                )
        );

        assertThrows(
                RecursoDuplicadoException.class,
                () -> fornecedorService.cadastrar(
                        new Fornecedor(
                                "Outro Fornecedor",
                                "12345678000199"
                        )
                )
        );
    }

    @Test
    void deveBuscarFornecedorPorId() {
        Fornecedor salvo = fornecedorService.cadastrar(
                new Fornecedor(
                        "Fornecedor Pesca Brasil",
                        "12345678000199"
                )
        );

        Fornecedor encontrado =
                fornecedorService.buscarPorId(salvo.getId());

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Fornecedor Pesca Brasil", encontrado.getRazaoSocial());
    }

    @Test
    void deveLancarExcecaoAoBuscarFornecedorInexistente() {
        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> fornecedorService.buscarPorId(999999L)
        );
    }

    @Test
    void deveListarFornecedores() {
        fornecedorService.cadastrar(
                new Fornecedor(
                        "Fornecedor Pesca Brasil",
                        "12345678000199"
                )
        );

        fornecedorService.cadastrar(
                new Fornecedor(
                        "Fornecedor Iscas Brasil",
                        "98765432000188"
                )
        );

        assertTrue(fornecedorService.listar().size() >= 2);
    }
}