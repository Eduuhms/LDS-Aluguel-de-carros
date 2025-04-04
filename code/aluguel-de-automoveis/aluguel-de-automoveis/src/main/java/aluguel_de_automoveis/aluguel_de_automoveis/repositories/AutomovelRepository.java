package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;
import java.util.Optional;

public interface AutomovelRepository extends JpaRepository<Automovel, String> {
    Optional<Automovel> findByMatricula(String matricula);
}