package com.curso.hefishing.service;

import com.curso.hefishing.domain.Fornecedor;
import com.curso.hefishing.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Fornecedor cadastrar(Fornecedor fornecedor) {
        if (fornecedorRepository.existsByCnpj(fornecedor.getCnpj())) {
            throw new RecursoDuplicadoException(
                    "Já existe um fornecedor com o CNPJ: " + fornecedor.getCnpj()
            );
        }

        return fornecedorRepository.save(fornecedor);
    }

    @Transactional(readOnly = true)
    public Fornecedor buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor não encontrado: " + id
                ));
    }

    @Transactional(readOnly = true)
    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }
}