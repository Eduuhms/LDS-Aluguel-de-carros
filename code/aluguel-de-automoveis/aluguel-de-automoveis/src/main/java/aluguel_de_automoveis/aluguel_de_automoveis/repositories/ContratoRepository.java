package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}