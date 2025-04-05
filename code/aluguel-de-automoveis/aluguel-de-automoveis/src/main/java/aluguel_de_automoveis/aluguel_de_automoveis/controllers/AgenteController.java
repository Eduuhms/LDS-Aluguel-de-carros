package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Agente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.services.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agentes")
public class AgenteController {
    
    @Autowired
    private AgenteService agenteService;
    
    @GetMapping
    public List<Agente> listarTodos() {
        return agenteService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Agente> buscarPorId(@PathVariable Long id) {
        return agenteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Agente> buscarPorCnpj(@PathVariable String cnpj) {
        return agenteService.buscarPorCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Agente> criar(@RequestBody Agente agente) {
        Agente novoAgente = agenteService.salvar(agente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Agente> atualizar(@PathVariable Long id, @RequestBody Agente agente) {
        return agenteService.buscarPorId(id)
                .map(agenteExistente -> {
                    agente.setId(id);
                    return ResponseEntity.ok(agenteService.salvar(agente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (agenteService.buscarPorId(id).isPresent()) {
            agenteService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{agenteId}/analisar-pedido/{pedidoId}")
    public ResponseEntity<Pedido> analisarPedidoFinanceiramente(
            @PathVariable Long agenteId, 
            @PathVariable Long pedidoId) {
        Pedido pedidoAnalisado = agenteService.analisarPedidoFinanceiramente(agenteId, pedidoId);
        if (pedidoAnalisado != null) {
            return ResponseEntity.ok(pedidoAnalisado);
        }
        return ResponseEntity.notFound().build();
    }
} 