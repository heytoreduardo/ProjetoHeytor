package com.curso.hefishing.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FornecedorTest {

    @Test
    void deveCriarFornecedorAtivo() {
        Fornecedor fornecedor = new Fornecedor(
                "Fornecedor Pesca Brasil",
                "12345678000199"
        );

        assertEquals("Fornecedor Pesca Brasil", fornecedor.getRazaoSocial());
        assertEquals("12345678000199", fornecedor.getCnpj());
        assertEquals(Status.ATIVO, fornecedor.getStatus());
    }

    @Test
    void deveRejeitarRazaoSocialVazia() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Fornecedor("", "12345678000199")
        );
    }

    @Test
    void deveRejeitarCnpjInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Fornecedor(
                        "Fornecedor Pesca Brasil",
                        "123"
                )
        );
    }

    @Test
    void deveInativarFornecedor() {
        Fornecedor fornecedor = new Fornecedor(
                "Fornecedor Pesca Brasil",
                "12345678000199"
        );

        fornecedor.inativar();

        assertEquals(Status.INATIVO, fornecedor.getStatus());
    }

    @Test
    void deveAtivarFornecedor() {
        Fornecedor fornecedor = new Fornecedor(
                "Fornecedor Pesca Brasil",
                "12345678000199"
        );

        fornecedor.inativar();
        fornecedor.ativar();

        assertEquals(Status.ATIVO, fornecedor.getStatus());
    }
}