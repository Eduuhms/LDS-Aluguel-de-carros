package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import java.util.List;

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

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ClienteRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados no sistema")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso")
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }
    
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @Parameter(description = "ID numérico do cliente", example = "1", required = true) 
            @PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna um cliente específico com base no CPF fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(
            @Parameter(description = "CPF do cliente (com ou sem pontuação)", example = "123.456.789-00", required = true) 
            @PathVariable String cpf) {
        return clienteService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Buscar cliente por RG", description = "Retorna um cliente específico com base no RG fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @GetMapping("/rg/{rg}")
    public ResponseEntity<Cliente> buscarPorRg(
            @Parameter(description = "RG do cliente", example = "1234567", required = true) 
            @PathVariable String rg) {
        return clienteService.buscarPorRg(rg)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Criar um novo cliente", description = "Cadastra um novo cliente no sistema")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    @PostMapping
    public ResponseEntity<Cliente> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do cliente",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cliente.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"usuario\": \"joaosilva\",\n" +
                               "  \"nome\": \"João Silva\",\n" +
                               "  \"email\": \"joao@example.com\",\n" +
                               "  \"senha\": \"senha123\",\n" +
                               "  \"cpf\": \"123.456.789-00\",\n" +
                               "  \"rg\": \"1234567\",\n" +
                               "  \"profissao\": \"Engenheiro\"\n" +
                               "}"
                    )
                )
            )
            @RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }
    
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @Parameter(description = "ID do cliente", example = "1", required = true) 
            @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados atualizados do cliente",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cliente.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"usuario\": \"joaosilva\",\n" +
                               "  \"nome\": \"João Silva\",\n" +
                               "  \"email\": \"joao.novo@example.com\",\n" +
                               "  \"senha\": \"novasenha123\",\n" +
                               "  \"cpf\": \"123.456.789-00\",\n" +
                               "  \"rg\": \"1234567\",\n" +
                               "  \"profissao\": \"Arquiteto\"\n" +
                               "}"
                    )
                )
            )
            @RequestBody Cliente cliente) {
        return clienteService.buscarPorId(id)
                .map(clienteExistente -> {
                    cliente.setId(id);
                    return ResponseEntity.ok(clienteService.salvar(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Excluir cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do cliente", example = "1", required = true) 
            @PathVariable Long id) {
        if (clienteService.buscarPorId(id).isPresent()) {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Criar pedido para um cliente", description = "Cria um novo pedido associado a um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PostMapping("/{clienteId}/pedidos")
    public ResponseEntity<Pedido> criarPedido(
            @Parameter(description = "ID do cliente", example = "1", required = true) 
            @PathVariable Long clienteId, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados do pedido",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"veiculo\": {\n" +
                               "    \"id\": 1\n" +
                               "  }\n" +
                               "}"
                    )
                )
            )
            @RequestBody Pedido pedido) {
        Pedido novoPedido = clienteService.introduzirPedido(clienteId, pedido);
        if (novoPedido != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Listar pedidos de um cliente", description = "Retorna todos os pedidos de um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<List<Pedido>> listarPedidos(
            @Parameter(description = "ID do cliente", example = "1", required = true) 
            @PathVariable Long clienteId) {
        if (clienteService.buscarPorId(clienteId).isPresent()) {
            List<Pedido> pedidos = clienteService.consultarPedidos(clienteId);
            return ResponseEntity.ok(pedidos);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Atualizar pedido", description = "Atualiza os dados de um pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    @PutMapping("/pedidos/{pedidoId}")
    public ResponseEntity<Pedido> atualizarPedido(
            @Parameter(description = "ID do pedido", example = "1", required = true) 
            @PathVariable Long pedidoId, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados atualizados do pedido",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"veiculo\": {\n" +
                               "    \"id\": 2\n" +
                               "  },\n" +
                               "  \"aprovado\": true\n" +
                               "}"
                    )
                )
            )
            @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = clienteService.modificarPedido(pedidoId, pedido);
        if (pedidoAtualizado != null) {
            return ResponseEntity.ok(pedidoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Cancelar pedido", description = "Remove um pedido do sistema")
    @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso")
    @DeleteMapping("/pedidos/{pedidoId}")
    public ResponseEntity<Void> cancelarPedido(
            @Parameter(description = "ID do pedido", example = "1", required = true) 
            @PathVariable Long pedidoId) {
        clienteService.cancelarPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Atualizar cliente por CPF", description = "Atualiza os dados de um cliente existente usando seu CPF como identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> atualizarPorCpf(
            @Parameter(description = "CPF do cliente (com ou sem pontuação)", example = "123.456.789-00", required = true) 
            @PathVariable String cpf, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados atualizados do cliente",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cliente.class),
                    examples = @ExampleObject(
                        value = "{\n" +
                               "  \"usuario\": \"joaosilva\",\n" +
                               "  \"nome\": \"João Silva\",\n" +
                               "  \"email\": \"joao.novo@example.com\",\n" +
                               "  \"senha\": \"novasenha123\",\n" +
                               "  \"rg\": \"1234567\",\n" +
                               "  \"profissao\": \"Arquiteto\"\n" +
                               "}"
                    )
                )
            )
            @RequestBody Cliente cliente) {
        return clienteService.buscarPorCpf(cpf)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    cliente.setCpf(cpf); // Garante que o CPF não seja modificado
                    return ResponseEntity.ok(clienteService.salvar(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}