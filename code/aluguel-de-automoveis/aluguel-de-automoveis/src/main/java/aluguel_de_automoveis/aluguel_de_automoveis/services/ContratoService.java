package aluguel_de_automoveis.aluguel_de_automoveis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Contrato;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ContratoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public Contrato salvar(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public Contrato atualizar(Long id, Contrato contratoAtualizado) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    contrato.setTipo(contratoAtualizado.getTipo());
                    contrato.setAutomovel(contratoAtualizado.getAutomovel());
                    contrato.setCliente(contratoAtualizado.getCliente());
                    contrato.setProprietario(contratoAtualizado.getProprietario());
                    return contratoRepository.save(contrato);
                }).orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    }

    public void deletar(Long id) {
        contratoRepository.deleteById(id);
    }
}