package aluguel_de_automoveis.aluguel_de_automoveis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Rendimento;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoService pedidoService;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Optional<Cliente> buscarPorRg(String rg) {
        return clienteRepository.findByRg(rg);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }

    public Pedido introduzirPedido(Long clienteId, Pedido pedido) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            pedido.setCliente(cliente);
            return pedidoService.salvar(pedido);
        }
        return null;
    }

    public void cancelarPedido(Long pedidoId) {
        pedidoService.excluir(pedidoId);
    }

    public List<Pedido> consultarPedidos(Long clienteId) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return pedidoService.buscarPorCliente(cliente);
        }
        return List.of();
    }

    public Pedido modificarPedido(Long pedidoId, Pedido pedidoAtualizado) {
        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(pedidoId);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            if (pedidoAtualizado.getVeiculo() != null) {
                pedido.setVeiculo(pedidoAtualizado.getVeiculo());
            }
            return pedidoService.salvar(pedido);
        }
        return null;
    }
    
    /**
     * Adiciona um rendimento à lista de rendimentos auferidos do cliente,
     * respeitando o limite máximo definido em Cliente.MAX_RENDIMENTOS_AUFERIDOS.
     * Se a lista já estiver no limite, o rendimento mais antigo será removido
     * para dar lugar ao novo (FIFO - First In, First Out).
     * 
     * @param clienteId ID do cliente
     * @param rendimento Objeto Rendimento a ser adicionado
     * @return Cliente atualizado com o novo rendimento ou null se o cliente não for encontrado
     */
    public Cliente adicionarRendimento(Long clienteId, Rendimento rendimento) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            
            // Define o cliente no rendimento
            rendimento.setCliente(cliente);
            
            // Se já atingiu o limite máximo, remove o primeiro rendimento (o mais antigo)
            if (cliente.getRendimentosAuferidos().size() >= Cliente.MAX_RENDIMENTOS_AUFERIDOS) {
                cliente.getRendimentosAuferidos().remove(0);
            }
            
            // Adiciona o novo rendimento
            cliente.getRendimentosAuferidos().add(rendimento);
            
            // Salva o cliente atualizado
            return clienteRepository.save(cliente);
        }
        return null;
    }
    
    /**
     * Adiciona um rendimento à lista de rendimentos auferidos do cliente,
     * utilizando o CPF como identificador e respeitando o limite máximo.
     * 
     * @param cpf CPF do cliente
     * @param rendimento Objeto Rendimento a ser adicionado
     * @return Cliente atualizado com o novo rendimento ou null se o cliente não for encontrado
     */
    public Cliente adicionarRendimentoPorCpf(String cpf, Rendimento rendimento) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpf);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            
            // Define o cliente no rendimento
            rendimento.setCliente(cliente);
            
            // Se já atingiu o limite máximo, remove o primeiro rendimento (o mais antigo)
            if (cliente.getRendimentosAuferidos().size() >= Cliente.MAX_RENDIMENTOS_AUFERIDOS) {
                cliente.getRendimentosAuferidos().remove(0);
            }
            
            // Adiciona o novo rendimento
            cliente.getRendimentosAuferidos().add(rendimento);
            
            // Salva o cliente atualizado
            return clienteRepository.save(cliente);
        }
        return null;
    }
    
    /**
     * Lista todos os rendimentos de um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Lista de rendimentos ou lista vazia se o cliente não for encontrado
     */
    public List<Rendimento> listarRendimentos(Long clienteId) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isPresent()) {
            return clienteOpt.get().getRendimentosAuferidos();
        }
        return List.of();
    }
    
    /**
     * Lista todos os rendimentos de um cliente específico usando o CPF como identificador.
     * 
     * @param cpf CPF do cliente
     * @return Lista de rendimentos ou lista vazia se o cliente não for encontrado
     */
    public List<Rendimento> listarRendimentosPorCpf(String cpf) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpf);
        if (clienteOpt.isPresent()) {
            return clienteOpt.get().getRendimentosAuferidos();
        }
        return List.of();
    }
}
