package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Banco;
import aluguel_de_automoveis.aluguel_de_automoveis.services.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {
    
    @Autowired
    private BancoService bancoService;
    
    @GetMapping
    public List<Banco> listarTodos() {
        return bancoService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Banco> buscarPorId(@PathVariable Long id) {
        return bancoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Banco> criar(@RequestBody Banco banco) {
        Banco novoBanco = bancoService.salvar(banco);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBanco);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Banco> atualizar(@PathVariable Long id, @RequestBody Banco banco) {
        return bancoService.buscarPorId(id)
                .map(bancoExistente -> {
                    banco.setId(id);
                    return ResponseEntity.ok(bancoService.salvar(banco));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (bancoService.buscarPorId(id).isPresent()) {
            bancoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 