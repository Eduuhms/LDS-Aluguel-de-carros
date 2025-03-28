package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
