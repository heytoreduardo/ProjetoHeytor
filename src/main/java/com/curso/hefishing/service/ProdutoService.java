package com.curso.hefishing.service;

import com.curso.hefishing.domain.Fornecedor;
import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.domain.Produto;
import com.curso.hefishing.repository.FornecedorRepository;
import com.curso.hefishing.repository.GrupoProdutoRepository;
import com.curso.hefishing.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final GrupoProdutoRepository grupoProdutoRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            GrupoProdutoRepository grupoProdutoRepository,
            FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.grupoProdutoRepository = grupoProdutoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Produto cadastrar(
            Produto produto,
            Long grupoId,
            Long fornecedorId) {

        if (produtoRepository.existsByCodigoBarras(
                produto.getCodigoBarras())) {

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

        if (fornecedorId != null) {
            Fornecedor fornecedor = fornecedorRepository
                    .findById(fornecedorId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor não encontrado: " + fornecedorId
                    ));

            produto.associarFornecedor(fornecedor);
        }

        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findByIdComRelacionamentos(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                    "Produto não encontrado: " + id
                ));
    }

    @Transactional(readOnly = true)
    public List<Produto> listar() {
        return produtoRepository.findAllComRelacionamentos();
    }
}

