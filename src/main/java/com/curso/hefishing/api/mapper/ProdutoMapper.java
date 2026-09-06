package com.curso.hefishing.api.mapper;

import com.curso.hefishing.api.dto.ProdutoRequest;
import com.curso.hefishing.api.dto.ProdutoResponse;
import com.curso.hefishing.domain.Produto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest request) {
        return new Produto(
                request.codigoBarras(),
                request.descricao(),
                request.saldoEstoque(),
                request.valorUnitario(),
                request.estoqueMinimo(),
                LocalDate.now()
        );
    }

    public ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getCodigoBarras(),
                produto.getDescricao(),
                produto.getSaldoEstoque(),
                produto.getValorUnitario(),
                produto.getEstoqueMinimo(),
                produto.calcularValorEstoque(),
                produto.getDataCadastro(),
                produto.getStatus(),
                produto.getGrupo().getId(),
                produto.getGrupo().getNome(),
                produto.getFornecedor() != null
                        ? produto.getFornecedor().getId()
                        : null,
                produto.getFornecedor() != null
                        ? produto.getFornecedor().getRazaoSocial()
                        : null
        );
    }
}
