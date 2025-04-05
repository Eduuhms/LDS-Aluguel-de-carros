package aluguel_de_automoveis.aluguel_de_automoveis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.AutomovelRepository;

@Service
public class AutomovelService {
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }
    
    public Automovel salvar(Automovel automovel) {
        return automovelRepository.save(automovel);
    }
    
    public Optional<Automovel> buscarPorId(Long id) {
        return automovelRepository.findById(id);
    }
    
    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return automovelRepository.findByMatricula(matricula);
    }
    
    public Optional<Automovel> buscarPorPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }
    
    public List<Automovel> buscarDisponiveisParaAluguel() {
        return automovelRepository.findByAlugadoFalse();
    }
    
    public List<Automovel> buscarPorMarcaModeloAnoDisponiveis(String marca, String modelo, int ano) {
        return automovelRepository.findByMarcaAndModeloAndAnoAndAlugadoFalse(marca, modelo, ano);
    }
    
    public void excluir(Long id) {
        automovelRepository.deleteById(id);
    }
    
    public Automovel marcarComoAlugado(Long id) {
        Optional<Automovel> automovelOpt = automovelRepository.findById(id);
        if (automovelOpt.isPresent()) {
            Automovel automovel = automovelOpt.get();
            automovel.setAlugado(true);
            return automovelRepository.save(automovel);
        }
        return null;
    }
    
    public Automovel marcarComoDisponivel(Long id) {
        Optional<Automovel> automovelOpt = automovelRepository.findById(id);
        if (automovelOpt.isPresent()) {
            Automovel automovel = automovelOpt.get();
            automovel.setAlugado(false);
            return automovelRepository.save(automovel);
        }
        return null;
    }
}