package aluguel_de_automoveis.aluguel_de_automoveis.services;

import aluguel_de_automoveis.aluguel_de_automoveis.dto.ContratoDTO;
import aluguel_de_automoveis.aluguel_de_automoveis.enums.TipoContrato;
import aluguel_de_automoveis.aluguel_de_automoveis.models.*;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private AutomovelRepository automovelRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public Contrato salvarComDTO(ContratoDTO dto) {
        Contrato contrato = fromDTO(dto);
        return contratoRepository.save(contrato);
    }

    public Contrato atualizarComDTO(Long id, ContratoDTO dto) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    Contrato atualizado = fromDTO(dto);
                    contrato.setTipo(atualizado.getTipo());
                    contrato.setAutomovel(atualizado.getAutomovel());
                    contrato.setCliente(atualizado.getCliente());
                    contrato.setProprietario(atualizado.getProprietario());
                    return contratoRepository.save(contrato);
                }).orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    }

    public void deletar(Long id) {
        contratoRepository.deleteById(id);
    }

    private Contrato fromDTO(ContratoDTO dto) {
        Automovel automovel = automovelRepository.findById(dto.getAutomovelMatricula())
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Usuario proprietario = usuarioRepository.findById(dto.getProprietarioId())
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

        Contrato contrato = new Contrato();
        contrato.setTipo(TipoContrato.valueOf(dto.getTipo()));
        contrato.setAutomovel(automovel);
        contrato.setCliente(cliente);
        contrato.setProprietario(proprietario);

        return contrato;
    }
}
