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

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        return clienteService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/rg/{rg}")
    public ResponseEntity<Cliente> buscarPorRg(@PathVariable String rg) {
        return clienteService.buscarPorRg(rg)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.buscarPorId(id)
                .map(clienteExistente -> {
                    cliente.setId(id);
                    return ResponseEntity.ok(clienteService.salvar(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (clienteService.buscarPorId(id).isPresent()) {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{clienteId}/pedidos")
    public ResponseEntity<Pedido> criarPedido(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
        Pedido novoPedido = clienteService.introduzirPedido(clienteId, pedido);
        if (novoPedido != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<List<Pedido>> listarPedidos(@PathVariable Long clienteId) {
        if (clienteService.buscarPorId(clienteId).isPresent()) {
            List<Pedido> pedidos = clienteService.consultarPedidos(clienteId);
            return ResponseEntity.ok(pedidos);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/pedidos/{pedidoId}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long pedidoId, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = clienteService.modificarPedido(pedidoId, pedido);
        if (pedidoAtualizado != null) {
            return ResponseEntity.ok(pedidoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/pedidos/{pedidoId}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long pedidoId) {
        clienteService.cancelarPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }
}