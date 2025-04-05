package aluguel_de_automoveis.aluguel_de_automoveis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ClienteRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
    
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    public List<Pedido> buscarPorCliente(Cliente cliente) {
        return pedidoRepository.findByCliente(cliente);
    }
    
    public void excluir(Long id) {
        pedidoRepository.deleteById(id);
    }
    
    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setCliente(pedidoAtualizado.getCliente());
                    pedido.setVeiculo(pedidoAtualizado.getVeiculo());
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
    
    public Pedido criarPedidoPorCpf(String cpf, Pedido pedido) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpf);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            pedido.setCliente(cliente);
            return pedidoRepository.save(pedido);
        }
        throw new RuntimeException("Cliente com CPF " + cpf + " não encontrado");
    }
}