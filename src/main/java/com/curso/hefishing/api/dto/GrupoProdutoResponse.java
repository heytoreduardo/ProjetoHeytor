package com.curso.hefishing.api.dto;

import com.curso.hefishing.domain.Status;

public record GrupoProdutoResponse(
        Long id,
        String nome,
        Status status
) {
}
