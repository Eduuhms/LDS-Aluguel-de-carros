package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos de aluguel")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos cadastrados")
    @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso")
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido com os dados fornecidos")
    @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso")
    @PostMapping
    public Pedido criar(
            @Parameter(description = "Dados do pedido", required = true, schema = @Schema(implementation = Pedido.class))
            @RequestBody Pedido pedido) {
        return pedidoService.salvar(pedido);
    }
    
    @Operation(summary = "Criar pedido por CPF do cliente", description = "Cria um novo pedido associado ao cliente com o CPF fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PostMapping("/cliente/cpf/{cpf}")
    public ResponseEntity<Pedido> criarPorCpf(
            @Parameter(description = "CPF do cliente", required = true) @PathVariable String cpf,
            @Parameter(description = "Dados do pedido", required = true, schema = @Schema(implementation = Pedido.class)) 
            @RequestBody Pedido pedido) {
        try {
            Pedido novoPedido = pedidoService.criarPedidoPorCpf(cpf, pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Atualizar pedido", description = "Atualiza os dados de um pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id,
            @Parameter(description = "Dados atualizados do pedido", required = true) @RequestBody Pedido pedido) {
        try {
            Pedido atualizado = pedidoService.atualizar(id, pedido);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir pedido", description = "Remove um pedido do sistema")
    @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}