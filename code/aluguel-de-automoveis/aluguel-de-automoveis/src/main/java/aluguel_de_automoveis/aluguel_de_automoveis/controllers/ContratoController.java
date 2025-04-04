package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import aluguel_de_automoveis.aluguel_de_automoveis.dto.ContratoDTO;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Contrato;
import aluguel_de_automoveis.aluguel_de_automoveis.services.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contratos")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    public List<Contrato> listarTodos() {
        return contratoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscarPorId(@PathVariable Long id) {
        Optional<Contrato> contrato = contratoService.buscarPorId(id);
        return contrato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contrato> criar(@RequestBody ContratoDTO contratoDTO) {
        try {
            Contrato contrato = contratoService.salvarComDTO(contratoDTO);
            return ResponseEntity.ok(contrato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrato> atualizar(@PathVariable Long id, @RequestBody ContratoDTO contratoDTO) {
        try {
            Contrato atualizado = contratoService.atualizarComDTO(id, contratoDTO);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
