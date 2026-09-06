package com.curso.hefishing.api.controller;

import com.curso.hefishing.api.dto.ProdutoRequest;
import com.curso.hefishing.api.dto.ProdutoResponse;
import com.curso.hefishing.api.mapper.ProdutoMapper;
import com.curso.hefishing.domain.Produto;
import com.curso.hefishing.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(
            ProdutoService produtoService,
            ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(
            @Valid @RequestBody ProdutoRequest request) {

        Produto produto = produtoMapper.toEntity(request);

        Produto salvo = produtoService.cadastrar(
                produto,
                request.grupoId(),
                request.fornecedorId()
        );

        ProdutoResponse response =
                produtoMapper.toResponse(salvo);

        URI location = URI.create(
                "/api/produtos/" + salvo.getId()
        );

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(
            @PathVariable Long id) {

        Produto produto = produtoService.buscarPorId(id);

        return ResponseEntity.ok(
                produtoMapper.toResponse(produto)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {

        List<ProdutoResponse> response =
                produtoService.listar()
                        .stream()
                        .map(produtoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
}
