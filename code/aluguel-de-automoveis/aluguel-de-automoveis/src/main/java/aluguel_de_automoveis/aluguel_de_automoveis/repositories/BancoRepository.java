package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
} 