package com.curso.hefishing.api.controller;

import com.curso.hefishing.api.dto.GrupoProdutoRequest;
import com.curso.hefishing.api.dto.GrupoProdutoResponse;
import com.curso.hefishing.api.mapper.GrupoProdutoMapper;
import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.service.GrupoProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/grupos-produtos")
public class GrupoProdutoController {

    private final GrupoProdutoService grupoProdutoService;
    private final GrupoProdutoMapper grupoProdutoMapper;

    public GrupoProdutoController(
            GrupoProdutoService grupoProdutoService,
            GrupoProdutoMapper grupoProdutoMapper) {
        this.grupoProdutoService = grupoProdutoService;
        this.grupoProdutoMapper = grupoProdutoMapper;
    }

    @PostMapping
    public ResponseEntity<GrupoProdutoResponse> cadastrar(
            @Valid @RequestBody GrupoProdutoRequest request) {

        GrupoProduto grupo = grupoProdutoMapper.toEntity(request);

        GrupoProduto salvo = grupoProdutoService.cadastrar(grupo);

        GrupoProdutoResponse response =
                grupoProdutoMapper.toResponse(salvo);

        URI location = URI.create(
                "/api/grupos-produtos/" + salvo.getId()
        );

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoProdutoResponse> buscarPorId(
            @PathVariable Long id) {

        GrupoProduto grupo = grupoProdutoService.buscarPorId(id);

        return ResponseEntity.ok(
                grupoProdutoMapper.toResponse(grupo)
        );
    }

    @GetMapping
    public ResponseEntity<List<GrupoProdutoResponse>> listar() {

        List<GrupoProdutoResponse> response =
                grupoProdutoService.listar()
                        .stream()
                        .map(grupoProdutoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }
}
