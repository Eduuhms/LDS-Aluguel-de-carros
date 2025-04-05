package aluguel_de_automoveis.aluguel_de_automoveis.services;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Agente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.AgenteRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenteService {
    
    @Autowired
    private AgenteRepository agenteRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    public List<Agente> listarTodos() {
        return agenteRepository.findAll();
    }
    
    public Agente salvar(Agente agente) {
        return agenteRepository.save(agente);
    }
    
    public Optional<Agente> buscarPorId(Long id) {
        return agenteRepository.findById(id);
    }
    
    public Optional<Agente> buscarPorCnpj(String cnpj) {
        return agenteRepository.findByCnpj(cnpj);
    }
    
    public void excluir(Long id) {
        agenteRepository.deleteById(id);
    }
    
    public Pedido analisarPedidoFinanceiramente(Long agenteId, Long pedidoId) {
        Optional<Agente> agenteOpt = agenteRepository.findById(agenteId);
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        
        if (agenteOpt.isPresent() && pedidoOpt.isPresent()) {
            Agente agente = agenteOpt.get();
            Pedido pedido = pedidoOpt.get();
            
            // Aqui seria implementada a lógica de análise financeira
            // Por enquanto vamos apenas retornar o pedido como aprovado
            
            return pedidoRepository.save(pedido);
        }
        
        return null;
    }
} 