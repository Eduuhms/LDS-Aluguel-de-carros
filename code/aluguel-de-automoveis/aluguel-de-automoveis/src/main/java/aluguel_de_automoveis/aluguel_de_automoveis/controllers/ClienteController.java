package aluguel_de_automoveis.aluguel_de_automoveis.controllers;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // READ - Listar todos
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // READ - Buscar por ID
    @GetMapping("/{id}")
    public Optional<Cliente> buscarCliente(@PathVariable Long id) {
        return clienteRepository.findById(id);
    }

    // CREATE - Criar novo cliente
    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // UPDATE - Atualizar cliente
    @PutMapping("/{id}")
    public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente dados) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        cliente.setNome(dados.getNome());
        return clienteRepository.save(cliente);
    }

    // DELETE - Remover cliente
    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}
