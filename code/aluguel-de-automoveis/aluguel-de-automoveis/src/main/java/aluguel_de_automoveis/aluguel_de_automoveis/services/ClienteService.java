package aluguel_de_automoveis.aluguel_de_automoveis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Rendimento;
import aluguel_de_automoveis.aluguel_de_automoveis.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Cliente salvar(Cliente cliente) {
        // Configura o relacionamento bidirecional com Endereco, se houver
        if (cliente.getEndereco() != null) {
            cliente.getEndereco().setCliente(cliente);
        }

        // Verifica e configura os rendimentos
        if (cliente.getRendimentos() != null) {
            if (cliente.getRendimentos().size() > Cliente.MAX_RENDIMENTOS_AUFERIDOS) {
                throw new RuntimeException("O cliente não pode ter mais de " + Cliente.MAX_RENDIMENTOS_AUFERIDOS + " rendimentos.");
            }

            for (Rendimento rendimento : cliente.getRendimentos()) {
                rendimento.setCliente(cliente);

                // Aqui assumimos que a entidade Empregador está corretamente configurada
                // com @ManyToOne(cascade = CascadeType.PERSIST) em Rendimento
                // Se não tiver, você precisa persistir o empregador manualmente
            }
        }

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    // Atualiza campos básicos
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setUsuario(clienteAtualizado.getUsuario());
                    cliente.setSenha(clienteAtualizado.getSenha());

                    // Atualiza campos específicos do Cliente
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setProfissao(clienteAtualizado.getProfissao());

                    // Atualiza endereço
                    if (clienteAtualizado.getEndereco() != null) {
                        clienteAtualizado.getEndereco().setCliente(cliente);
                        cliente.setEndereco(clienteAtualizado.getEndereco());
                    }

                    // Atualiza rendimentos
                    if (clienteAtualizado.getRendimentos() != null) {
                        if (clienteAtualizado.getRendimentos().size() > Cliente.MAX_RENDIMENTOS_AUFERIDOS) {
                            throw new RuntimeException("O cliente não pode ter mais de " + Cliente.MAX_RENDIMENTOS_AUFERIDOS + " rendimentos.");
                        }

                        for (Rendimento rendimento : clienteAtualizado.getRendimentos()) {
                            rendimento.setCliente(cliente);
                        }

                        cliente.setRendimentos(clienteAtualizado.getRendimentos());
                    }

                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
