package com.curso.hefishing.repository;

import com.curso.hefishing.domain.GrupoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoProdutoRepository extends JpaRepository<GrupoProduto, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    Optional<GrupoProduto> findByNomeIgnoreCase(String nome);
}
