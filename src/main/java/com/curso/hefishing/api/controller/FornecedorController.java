package com.curso.hefishing.api.controller;

import com.curso.hefishing.api.dto.FornecedorRequest;
import com.curso.hefishing.api.dto.FornecedorResponse;
import com.curso.hefishing.api.mapper.FornecedorMapper;
import com.curso.hefishing.domain.Fornecedor;
import com.curso.hefishing.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;
    private final FornecedorMapper fornecedorMapper;

    public FornecedorController(
            FornecedorService fornecedorService,
            FornecedorMapper fornecedorMapper) {
        this.fornecedorService = fornecedorService;
        this.fornecedorMapper = fornecedorMapper;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponse> cadastrar(
            @Valid @RequestBody FornecedorRequest request) {

        Fornecedor fornecedor = fornecedorMapper.toEntity(request);

        Fornecedor salvo = fornecedorService.cadastrar(fornecedor);

        FornecedorResponse response =
                fornecedorMapper.toResponse(salvo);

        URI location = URI.create(
                "/api/fornecedores/" + salvo.getId()
        );

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscarPorId(
            @PathVariable Long id) {

        Fornecedor fornecedor = fornecedorService.buscarPorId(id);

        return ResponseEntity.ok(
                fornecedorMapper.toResponse(fornecedor)
        );
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> listar() {

        List<FornecedorResponse> response =
                fornecedorService.listar()
                        .stream()
                        .map(fornecedorMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
}
