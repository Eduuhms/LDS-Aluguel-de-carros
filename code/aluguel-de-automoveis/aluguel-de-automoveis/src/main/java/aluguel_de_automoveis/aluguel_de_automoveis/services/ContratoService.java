package aluguel_de_automoveis.aluguel_de_automoveis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aluguel_de_automoveis.aluguel_de_automoveis.dto.ContratoDTO;
import aluguel_de_automoveis.aluguel_de_automoveis.enums.TipoContrato;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Contrato;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Usuario;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.AutomovelRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ClienteRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ContratoRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.PedidoRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.UsuarioRepository;

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
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }
    
    public Contrato salvar(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public Contrato salvarComDTO(ContratoDTO dto) {
        Contrato contrato = fromDTO(dto);
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

    private Contrato fromDTO(ContratoDTO dto) {
        Automovel automovel = automovelRepository.findByMatricula(dto.getAutomovelMatricula())
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
        
        // Associar ao pedido se o pedidoId for fornecido
        if (dto.getPedidoId() != null) {
            Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
            contrato.setPedido(pedido);
        }

        return contrato;
    }

    public List<Contrato> buscarPorCliente(Cliente cliente) {
        return contratoRepository.findByCliente(cliente);
    }

    public List<Contrato> buscarPorTipo(TipoContrato tipo) {
        return contratoRepository.findByTipo(tipo);
    }

    public void excluir(Long id) {
        contratoRepository.deleteById(id);
    }

    public Contrato registrarPropriedade(Long contratoId, Usuario proprietario) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(contratoId);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.setProprietario(proprietario);
            return contratoRepository.save(contrato);
        }
        return null;
    }

    public Contrato criarContratoDePedido(Long pedidoId, TipoContrato tipoContrato, Usuario proprietario) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        Contrato contrato = new Contrato();
        contrato.setTipo(tipoContrato);
        contrato.setAutomovel(pedido.getVeiculo());
        contrato.setCliente(pedido.getCliente());
        contrato.setProprietario(proprietario);
        contrato.setPedido(pedido);
        
        return contratoRepository.save(contrato);
    }
}
