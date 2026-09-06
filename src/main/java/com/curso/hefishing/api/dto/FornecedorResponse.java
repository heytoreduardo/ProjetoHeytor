package com.curso.hefishing.api.dto;

import com.curso.hefishing.domain.Status;

public record FornecedorResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        Status status
) {
}
