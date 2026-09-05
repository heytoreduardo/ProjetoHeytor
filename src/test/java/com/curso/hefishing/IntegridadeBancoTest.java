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

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class IntegridadeBancoTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void deveRejeitarSaldoEstoqueNegativoNoBanco() {
        GrupoProduto grupo = new GrupoProduto("Iscas de Pesca");

        Produto produto = new Produto(
                "7899876543210",
                "Isca artificial teste",
                new BigDecimal("5.000"),
                new BigDecimal("39.90"),
                LocalDate.now()
        );

        grupo.adicionarProduto(produto);

        entityManager.persist(grupo);
        entityManager.persist(produto);
        entityManager.flush();

        assertThrows(
                Exception.class,
                () -> entityManager.createNativeQuery(
                        "UPDATE produto SET saldo_estoque = -1 WHERE id = :id"
                ).setParameter("id", produto.getId())
                 .executeUpdate()
        );
    }
}
