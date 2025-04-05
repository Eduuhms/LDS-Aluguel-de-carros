package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    Optional<Agente> findByCnpj(String cnpj);
} 