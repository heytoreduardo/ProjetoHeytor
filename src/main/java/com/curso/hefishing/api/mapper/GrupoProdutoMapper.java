package com.curso.hefishing.api.mapper;

import com.curso.hefishing.api.dto.GrupoProdutoRequest;
import com.curso.hefishing.api.dto.GrupoProdutoResponse;
import com.curso.hefishing.domain.GrupoProduto;
import org.springframework.stereotype.Component;

@Component
public class GrupoProdutoMapper {

    public GrupoProduto toEntity(GrupoProdutoRequest request) {
        return new GrupoProduto(request.nome());
    }

    public GrupoProdutoResponse toResponse(GrupoProduto grupoProduto) {
        return new GrupoProdutoResponse(
                grupoProduto.getId(),
                grupoProduto.getNome(),
                grupoProduto.getStatus()
        );
    }
}
