package aluguel_de_automoveis.aluguel_de_automoveis.services;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Empresa;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.EmpresaRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
    
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    
    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }
    
    public void excluir(Long id) {
        empresaRepository.deleteById(id);
    }
    
    public Pedido avaliarPedido(Long empresaId, Long pedidoId) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        
        if (empresaOpt.isPresent() && pedidoOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            Pedido pedido = pedidoOpt.get();
            
            // Aqui seria implementada a lógica de avaliação do pedido
            // Por enquanto apenas retornamos o pedido
            
            return pedidoRepository.save(pedido);
        }
        
        return null;
    }
    
    public boolean gerarContrato(Long empresaId, Long pedidoId) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        
        if (empresaOpt.isPresent() && pedidoOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            Pedido pedido = pedidoOpt.get();
            
            // Aqui seria implementada a lógica para gerar contrato
            // Por enquanto retornamos true indicando sucesso
            
            return true;
        }
        
        return false;
    }
} 