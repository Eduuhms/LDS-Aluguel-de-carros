package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Empresa;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @GetMapping
    public List<Empresa> listarTodas() {
        return empresaService.listarTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Empresa> criar(@RequestBody Empresa empresa) {
        Empresa novaEmpresa = empresaService.salvar(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
        return empresaService.buscarPorId(id)
                .map(empresaExistente -> {
                    empresa.setId(id);
                    return ResponseEntity.ok(empresaService.salvar(empresa));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (empresaService.buscarPorId(id).isPresent()) {
            empresaService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{empresaId}/avaliar-pedido/{pedidoId}")
    public ResponseEntity<Pedido> avaliarPedido(
            @PathVariable Long empresaId,
            @PathVariable Long pedidoId) {
        Pedido pedidoAvaliado = empresaService.avaliarPedido(empresaId, pedidoId);
        if (pedidoAvaliado != null) {
            return ResponseEntity.ok(pedidoAvaliado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{empresaId}/gerar-contrato/{pedidoId}")
    public ResponseEntity<String> gerarContrato(
            @PathVariable Long empresaId,
            @PathVariable Long pedidoId) {
        boolean sucesso = empresaService.gerarContrato(empresaId, pedidoId);
        if (sucesso) {
            return ResponseEntity.ok("Contrato gerado com sucesso");
        }
        return ResponseEntity.notFound().build();
    }
} 