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

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findById(cpf);
    }

    public Cliente salvar(Cliente cliente) {
        if (cliente.getEndereco() != null) {
            cliente.getEndereco().setCliente(cliente);
        }
       if (cliente.getRendimentos() != null && cliente.getRendimentos().size() > 3) {
            throw new RuntimeException("O cliente não pode ter mais de 3 rendimentos.");
        }

        if (cliente.getRendimentos() != null){
            for (Rendimento rendimento : cliente.getRendimentos()) {
                rendimento.setCliente(cliente);
            }
        }        

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(String cpf, Cliente clienteAtualizado) {
        return clienteRepository.findById(cpf)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setProfissao(clienteAtualizado.getProfissao());
                    // Atualiza endereço corretamente
                    if (clienteAtualizado.getEndereco() != null) {
                        clienteAtualizado.getEndereco().setCliente(cliente);
                        cliente.setEndereco(clienteAtualizado.getEndereco());
                    }

                    // Atualiza rendimentos garantindo que não ultrapassem o limite de 3
                    if (clienteAtualizado.getRendimentos().size() > 3) {
                        throw new RuntimeException("O cliente não pode ter mais de 3 rendimentos.");
                    }
                    
                    if (clienteAtualizado.getRendimentos() != null){
                        for (Rendimento rendimento : clienteAtualizado.getRendimentos()) {
                            rendimento.setCliente(cliente);
                        }
                        cliente.setRendimentos(clienteAtualizado.getRendimentos());
                    }


                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void deletar(String cpf) {
        clienteRepository.deleteById(cpf);
    }
}
