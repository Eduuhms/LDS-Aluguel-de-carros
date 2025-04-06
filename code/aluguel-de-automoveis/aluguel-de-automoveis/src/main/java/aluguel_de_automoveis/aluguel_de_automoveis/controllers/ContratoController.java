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

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Contrato> listarTodos() {
        return contratoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscarPorId(@PathVariable Long id) {
        return contratoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public List<Contrato> listarPorTipo(@PathVariable TipoContrato tipo) {
        return contratoService.buscarPorTipo(tipo);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Contrato>> listarPorCliente(@PathVariable Long clienteId) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(clienteId);
        if (clienteOpt.isPresent()) {
            List<Contrato> contratos = contratoService.buscarPorCliente(clienteOpt.get());
            return ResponseEntity.ok(contratos);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contrato> criar(@RequestBody Contrato contrato) {
        Contrato novoContrato = contratoService.salvar(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
    }

    @PostMapping("/dto")
    public ResponseEntity<Contrato> criarComDTO(@RequestBody ContratoDTO dto) {
        try {
            Contrato novoContrato = contratoService.salvarComDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrato> atualizar(@PathVariable Long id, @RequestBody Contrato contrato) {
        try {
            contrato.setId(id);
            Contrato contratoAtualizado = contratoService.atualizar(id, contrato);
            return ResponseEntity.ok(contratoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/dto")
    public ResponseEntity<Contrato> atualizarComDTO(@PathVariable Long id, @RequestBody ContratoDTO dto) {
        try {
            Contrato contratoAtualizado = contratoService.atualizarComDTO(id, dto);
            return ResponseEntity.ok(contratoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (contratoService.buscarPorId(id).isPresent()) {
            contratoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{contratoId}/registrar-propriedade/{usuarioId}")
    public ResponseEntity<Contrato> registrarPropriedade(@PathVariable Long contratoId, @PathVariable Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);
        if (usuarioOpt.isPresent()) {
            Contrato contrato = contratoService.registrarPropriedade(contratoId, usuarioOpt.get());
            if (contrato != null) {
                return ResponseEntity.ok(contrato);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/pedido/{pedidoId}/usuario/{usuarioId}")
    public ResponseEntity<Contrato> criarContratoDePedido(
            @PathVariable Long pedidoId, 
            @PathVariable Long usuarioId,
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
