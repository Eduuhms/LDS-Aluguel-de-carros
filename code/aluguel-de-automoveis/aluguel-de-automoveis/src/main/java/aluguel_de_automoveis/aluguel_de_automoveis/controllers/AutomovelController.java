package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;
import aluguel_de_automoveis.aluguel_de_automoveis.services.AutomovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/automoveis")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AutomovelController {

    @Autowired
    private AutomovelService automovelService;

    @GetMapping
    public List<Automovel> listarTodos() {
        return automovelService.listarTodos();
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Automovel> buscarPorMatricula(@PathVariable String matricula) {
        Optional<Automovel> automovel = automovelService.buscarPorMatricula(matricula);
        return automovel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Automovel criar(@RequestBody Automovel automovel) {
        return automovelService.salvar(automovel);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Automovel> atualizar(@PathVariable String matricula, @RequestBody Automovel automovel) {
        try {
            Automovel atualizado = automovelService.atualizar(matricula, automovel);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {
        automovelService.deletar(matricula);
        return ResponseEntity.noContent().build();
    }
}