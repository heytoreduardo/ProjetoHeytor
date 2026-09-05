package com.curso.hefishing.service;

import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.repository.GrupoProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoProdutoService {

    private final GrupoProdutoRepository grupoProdutoRepository;

    public GrupoProdutoService(GrupoProdutoRepository grupoProdutoRepository) {
        this.grupoProdutoRepository = grupoProdutoRepository;
    }

    @Transactional
    public GrupoProduto cadastrar(GrupoProduto grupoProduto) {
        if (grupoProdutoRepository.existsByNomeIgnoreCase(grupoProduto.getNome())) {
            throw new RecursoDuplicadoException(
                    "Já existe um grupo de produto com o nome: " + grupoProduto.getNome()
            );
        }

        return grupoProdutoRepository.save(grupoProduto);
    }

    @Transactional(readOnly = true)
    public GrupoProduto buscarPorId(Long id) {
        return grupoProdutoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Grupo de produto não encontrado: " + id
                ));
    }

    @Transactional(readOnly = true)
    public List<GrupoProduto> listar() {
        return grupoProdutoRepository.findAll();
    }
}
