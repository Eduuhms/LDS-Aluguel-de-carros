package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @EntityGraph(attributePaths = "endereco")
    Optional<Cliente> findByCpf(String cpf);
}