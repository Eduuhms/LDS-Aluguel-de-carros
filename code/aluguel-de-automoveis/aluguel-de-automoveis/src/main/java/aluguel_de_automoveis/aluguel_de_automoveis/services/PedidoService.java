package aluguel_de_automoveis.aluguel_de_automoveis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.PedidoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setCliente(pedidoAtualizado.getCliente());
                    pedido.setAutomovel(pedidoAtualizado.getAutomovel());
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}