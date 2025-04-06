package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aluguel_de_automoveis.aluguel_de_automoveis.dto.ContratoDTO;
import aluguel_de_automoveis.aluguel_de_automoveis.enums.TipoContrato;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Contrato;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Usuario;
import aluguel_de_automoveis.aluguel_de_automoveis.services.ClienteService;
import aluguel_de_automoveis.aluguel_de_automoveis.services.ContratoService;
import aluguel_de_automoveis.aluguel_de_automoveis.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos", description = "API para gerenciamento de contratos de aluguel")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos os contratos", description = "Retorna uma lista com todos os contratos cadastrados")
    @ApiResponse(responseCode = "200", description = "Contratos encontrados com sucesso")
    @GetMapping
    public List<Contrato> listarTodos() {
        return contratoService.listarTodos();
    }

    @Operation(summary = "Buscar contrato por ID", description = "Retorna um contrato específico com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscarPorId(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id) {
        return contratoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar contratos por tipo", description = "Retorna uma lista de contratos do tipo especificado")
    @ApiResponse(responseCode = "200", description = "Contratos encontrados com sucesso")
    @GetMapping("/tipo/{tipo}")
    public List<Contrato> listarPorTipo(
            @Parameter(description = "Tipo do contrato (NORMAL ou CREDITO)", required = true) @PathVariable TipoContrato tipo) {
        return contratoService.buscarPorTipo(tipo);
    }

    @Operation(summary = "Listar contratos por cliente", description = "Retorna uma lista de contratos de um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contratos encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Contrato>> listarPorCliente(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long clienteId) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (clienteOpt.isPresent()) {
            List<Contrato> contratos = contratoService.buscarPorCliente(clienteOpt.get());
            return ResponseEntity.ok(contratos);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Criar um novo contrato", description = "Cria um novo contrato com os dados fornecidos")
    @ApiResponse(responseCode = "201", description = "Contrato criado com sucesso")
    @PostMapping
    public ResponseEntity<Contrato> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do contrato",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Contrato.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"tipo\": \"NORMAL\",\n" +
                               "  \"automovel\": {\n" +
                               "    \"id\": 1\n" +
                               "  },\n" +
                               "  \"cliente\": {\n" +
                               "    \"id\": 1\n" +
                               "  },\n" +
                               "  \"proprietario\": {\n" +
                               "    \"id\": 1\n" +
                               "  }\n" +
                               "}"
                    )
                )
            )
            @RequestBody Contrato contrato) {
        Contrato novoContrato = contratoService.salvar(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
    }

    @Operation(summary = "Criar contrato com DTO", description = "Cria um novo contrato a partir de um DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contrato criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping("/dto")
    public ResponseEntity<Contrato> criarComDTO(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "DTO do contrato",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ContratoDTO.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"tipo\": \"NORMAL\",\n" +
                               "  \"automovelMatricula\": \"ABC1234\",\n" +
                               "  \"clienteId\": 1,\n" +
                               "  \"proprietarioId\": 1,\n" +
                               "  \"pedidoId\": 1\n" +
                               "}"
                    )
                )
            )
            @RequestBody ContratoDTO dto) {
        try {
            Contrato novoContrato = contratoService.salvarComDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Atualizar contrato", description = "Atualiza os dados de um contrato existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Contrato> atualizar(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do contrato",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Contrato.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"tipo\": \"CREDITO\",\n" +
                               "  \"automovel\": {\n" +
                               "    \"id\": 2\n" +
                               "  },\n" +
                               "  \"cliente\": {\n" +
                               "    \"id\": 1\n" +
                               "  },\n" +
                               "  \"proprietario\": {\n" +
                               "    \"id\": 2\n" +
                               "  }\n" +
                               "}"
                    )
                )
            )
            @RequestBody Contrato contrato) {
        try {
            contrato.setId(id);
            Contrato contratoAtualizado = contratoService.atualizar(id, contrato);
            return ResponseEntity.ok(contratoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualizar contrato com DTO", description = "Atualiza os dados de um contrato existente usando DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    @PutMapping("/{id}/dto")
    public ResponseEntity<Contrato> atualizarComDTO(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "DTO do contrato",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ContratoDTO.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"tipo\": \"CREDITO\",\n" +
                               "  \"automovelMatricula\": \"XYZ5678\",\n" +
                               "  \"clienteId\": 1,\n" +
                               "  \"proprietarioId\": 3\n" +
                               "}"
                    )
                )
            )
            @RequestBody ContratoDTO dto) {
        try {
            Contrato contratoAtualizado = contratoService.atualizarComDTO(id, dto);
            return ResponseEntity.ok(contratoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir contrato", description = "Remove um contrato do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contrato excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id) {
        if (contratoService.buscarPorId(id).isPresent()) {
            contratoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Registrar proprietário", description = "Associa um usuário como proprietário de um contrato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário registrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato ou usuário não encontrado", content = @Content)
    })
    @PutMapping("/{contratoId}/registrar-propriedade/{usuarioId}")
    public ResponseEntity<Contrato> registrarPropriedade(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long contratoId,
            @Parameter(description = "ID do usuário (proprietário)", required = true) @PathVariable Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);
        if (usuarioOpt.isPresent()) {
            Contrato contrato = contratoService.registrarPropriedade(contratoId, usuarioOpt.get());
            if (contrato != null) {
                return ResponseEntity.ok(contrato);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Criar contrato a partir de pedido", description = "Cria um novo contrato baseado em um pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contrato criado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido ou usuário não encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Tipo de contrato inválido", content = @Content)
    })
    @PostMapping("/pedido/{pedidoId}/usuario/{usuarioId}")
    public ResponseEntity<Contrato> criarContratoDePedido(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId, 
            @Parameter(description = "ID do usuário (proprietário)", required = true) @PathVariable Long usuarioId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Tipo do contrato (NORMAL ou CREDITO)",
                required = true,
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string", allowableValues = {"NORMAL", "CREDITO"}),
                    examples = @ExampleObject(value = "NORMAL")
                )
            )
            @RequestBody String tipoContrato) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);
            if (usuarioOpt.isPresent()) {
                TipoContrato tipo = TipoContrato.valueOf(tipoContrato);
                Contrato novoContrato = contratoService.criarContratoDePedido(pedidoId, tipo, usuarioOpt.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
