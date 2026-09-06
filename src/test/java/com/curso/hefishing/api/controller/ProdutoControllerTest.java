package com.curso.hefishing.api.controller;

import com.curso.hefishing.domain.Fornecedor;
import com.curso.hefishing.domain.GrupoProduto;
import com.curso.hefishing.repository.FornecedorRepository;
import com.curso.hefishing.repository.GrupoProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProdutoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GrupoProdutoRepository grupoProdutoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private MockMvc mockMvc;

    private GrupoProduto grupo;
    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();

        grupo = new GrupoProduto("Grupo Teste");
        grupo = grupoProdutoRepository.save(grupo);

        fornecedor = new Fornecedor(
            "Fornecedor Teste",
            "12345678000199"
        );
        fornecedor = fornecedorRepository.save(fornecedor);
    }

    @Test
    void deveCadastrarProduto() throws Exception {

        String json = """
                {
                    "codigoBarras": "7899999999999",
                    "descricao": "Isca Artificial Teste",
                    "saldoEstoque": 10.000,
                    "valorUnitario": 29.90,
                    "estoqueMinimo": 2.000,
                    "grupoId": %d,
                    "fornecedorId": %d
                }
                """.formatted(grupo.getId(), fornecedor.getId());

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.codigoBarras")
                .value("7899999999999"))
            .andExpect(jsonPath("$.descricao")
                .value("Isca Artificial Teste"))
            .andExpect(jsonPath("$.saldoEstoque")
                .value(10.000))
            .andExpect(jsonPath("$.valorUnitario")
                .value(29.90))
            .andExpect(jsonPath("$.estoqueMinimo")
                .value(2.000))
            .andExpect(jsonPath("$.grupoId")
                .value(grupo.getId()))
            .andExpect(jsonPath("$.fornecedorId")
                .value(fornecedor.getId()));
    }

    @Test
    void deveBuscarProdutoPorId() throws Exception {

        String json = """
                {
                    "codigoBarras": "7898888888888",
                    "descricao": "Molinete Teste",
                    "saldoEstoque": 5.000,
                    "valorUnitario": 199.90,
                    "estoqueMinimo": 1.000,
                    "grupoId": %d,
                    "fornecedorId": %d
                }
                """.formatted(grupo.getId(), fornecedor.getId());

        String location = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        Long id = Long.valueOf(
            location.substring(location.lastIndexOf("/") + 1)
        );

        mockMvc.perform(get("/api/produtos/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.descricao")
                .value("Molinete Teste"))
            .andExpect(jsonPath("$.codigoBarras")
                .value("7898888888888"));
    }

    @Test
    void deveRetornar404QuandoProdutoNaoExiste() throws Exception {

        mockMvc.perform(get("/api/produtos/999999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void deveRetornar400QuandoDadosInvalidos() throws Exception {

        String json = """
                {
                    "codigoBarras": "",
                    "descricao": "",
                    "saldoEstoque": -10,
                    "valorUnitario": -5,
                    "estoqueMinimo": -1,
                    "grupoId": 0
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.fields").isMap());
    }

    @Test
    void deveListarProdutos() throws Exception {

        mockMvc.perform(get("/api/produtos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
