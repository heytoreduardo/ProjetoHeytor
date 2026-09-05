package com.curso.hefishing.service;

import com.curso.hefishing.domain.Produto;
import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.repository.ProdutoRepository;
import com.curso.hefishing.repository.GrupoProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final GrupoProdutoRepository grupoProdutoRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            GrupoProdutoRepository grupoProdutoRepository) {
        this.produtoRepository = produtoRepository;
        this.grupoProdutoRepository = grupoProdutoRepository;
    }

    @Transactional
    public Produto cadastrar(Produto produto, Long grupoId) {
        if (produtoRepository.existsByCodigoBarras(produto.getCodigoBarras())) {
            throw new RecursoDuplicadoException(
                    "Já existe um produto com o código de barras: "
                            + produto.getCodigoBarras()
            );
        }

        GrupoProduto grupo = grupoProdutoRepository.findById(grupoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Grupo de produto não encontrado: " + grupoId
                ));

        grupo.adicionarProduto(produto);

        return produtoRepository.save(produto);
    }
}
