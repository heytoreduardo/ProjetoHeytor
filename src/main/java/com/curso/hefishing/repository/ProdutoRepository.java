package com.curso.hefishing.repository;

import com.curso.hefishing.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarras(String codigoBarras);

    List<Produto> findByGrupoId(Long grupoId);

    List<Produto> findByStatus(com.curso.hefishing.domain.Status status);

    @Query("""
            select p
            from Produto p
            join fetch p.grupo
            left join fetch p.fornecedor
            """)
    List<Produto> findAllComRelacionamentos();

    @Query("""
            select p
            from Produto p
            join fetch p.grupo
            left join fetch p.fornecedor
            where p.id = :id
            """)
    Optional<Produto> findByIdComRelacionamentos(Long id);
}
