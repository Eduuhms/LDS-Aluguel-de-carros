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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;
import aluguel_de_automoveis.aluguel_de_automoveis.services.AutomovelService;

@RestController
@RequestMapping("/api/automoveis")
public class AutomovelController {
    
    @Autowired
    private AutomovelService automovelService;
    
    @GetMapping
    public List<Automovel> listarTodos() {
        return automovelService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Automovel> buscarPorId(@PathVariable Long id) {
        return automovelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Automovel> buscarPorMatricula(@PathVariable String matricula) {
        return automovelService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/placa/{placa}")
    public ResponseEntity<Automovel> buscarPorPlaca(@PathVariable String placa) {
        return automovelService.buscarPorPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/disponiveis")
    public List<Automovel> listarDisponiveis() {
        return automovelService.buscarDisponiveisParaAluguel();
    }
    
    @GetMapping("/disponiveis/filtro")
    public List<Automovel> buscarDisponiveisPorFiltro(
            @RequestParam String marca, 
            @RequestParam String modelo, 
            @RequestParam int ano) {
        return automovelService.buscarPorMarcaModeloAnoDisponiveis(marca, modelo, ano);
    }
    
    @PostMapping
    public ResponseEntity<Automovel> criar(@RequestBody Automovel automovel) {
        Automovel novoAutomovel = automovelService.salvar(automovel);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAutomovel);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Automovel> atualizar(@PathVariable Long id, @RequestBody Automovel automovel) {
        return automovelService.buscarPorId(id)
                .map(automovelExistente -> {
                    automovel.setId(id);
                    return ResponseEntity.ok(automovelService.salvar(automovel));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (automovelService.buscarPorId(id).isPresent()) {
            automovelService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/marcar-como-alugado")
    public ResponseEntity<Automovel> marcarComoAlugado(@PathVariable Long id) {
        Automovel automovel = automovelService.marcarComoAlugado(id);
        if (automovel != null) {
            return ResponseEntity.ok(automovel);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/marcar-como-disponivel")
    public ResponseEntity<Automovel> marcarComoDisponivel(@PathVariable Long id) {
        Automovel automovel = automovelService.marcarComoDisponivel(id);
        if (automovel != null) {
            return ResponseEntity.ok(automovel);
        }
        return ResponseEntity.notFound().build();
    }
}