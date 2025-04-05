package aluguel_de_automoveis.aluguel_de_automoveis.services;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Banco;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoService {
    
    @Autowired
    private BancoRepository bancoRepository;
    
    public List<Banco> listarTodos() {
        return bancoRepository.findAll();
    }
    
    public Banco salvar(Banco banco) {
        return bancoRepository.save(banco);
    }
    
    public Optional<Banco> buscarPorId(Long id) {
        return bancoRepository.findById(id);
    }
    
    public void excluir(Long id) {
        bancoRepository.deleteById(id);
    }
} 