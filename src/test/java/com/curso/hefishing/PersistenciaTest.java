package com.curso.hefishing;

import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.domain.Produto;
import jakarta.persistence.EntityManager;
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
class PersistenciaTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void devePersistirGrupoEProdutoComRelacionamento() {
        GrupoProduto grupo = new GrupoProduto("Varas de Pesca");

        Produto produto = new Produto(
                "7891234567890",
                "Vara de pesca teste",
                new BigDecimal("10.000"),
                new BigDecimal("199.90"),
                LocalDate.now()
        );

        grupo.adicionarProduto(produto);

        entityManager.persist(grupo);
        entityManager.persist(produto);
        entityManager.flush();

        assertNotNull(grupo.getId());
        assertNotNull(produto.getId());

        entityManager.clear();

        Produto produtoPersistido = entityManager.find(Produto.class, produto.getId());

        assertNotNull(produtoPersistido);
        assertEquals("7891234567890", produtoPersistido.getCodigoBarras());
        assertEquals("Vara de pesca teste", produtoPersistido.getDescricao());
        assertEquals("Varas de Pesca", produtoPersistido.getGrupo().getNome());
    }
}
