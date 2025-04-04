package aluguel_de_automoveis.aluguel_de_automoveis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.AutomovelRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AutomovelService {

    @Autowired
    private AutomovelRepository automovelRepository;

    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return automovelRepository.findById(matricula);
    }

    public Automovel salvar(Automovel automovel) {
        return automovelRepository.save(automovel);
    }

    public Automovel atualizar(String matricula, Automovel automovelAtualizado) {
        return automovelRepository.findById(matricula)
                .map(automovel -> {
                    automovel.setAno(automovelAtualizado.getAno());
                    automovel.setMarca(automovelAtualizado.getMarca());
                    automovel.setModelo(automovelAtualizado.getModelo());
                    automovel.setPlaca(automovelAtualizado.getPlaca());
                    automovel.setAlugado(automovelAtualizado.isAlugado());
                    return automovelRepository.save(automovel);
                }).orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
    }

    public void deletar(String matricula) {
        automovelRepository.deleteById(matricula);
    }
}