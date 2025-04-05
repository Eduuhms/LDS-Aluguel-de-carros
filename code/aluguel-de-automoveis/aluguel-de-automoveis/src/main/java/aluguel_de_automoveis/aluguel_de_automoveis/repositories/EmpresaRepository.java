package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
} 