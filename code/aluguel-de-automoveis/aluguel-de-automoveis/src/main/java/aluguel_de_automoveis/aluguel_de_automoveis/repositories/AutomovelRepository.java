package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Automovel;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    Optional<Automovel> findByMatricula(String matricula);
    Optional<Automovel> findByPlaca(String placa);
    List<Automovel> findByAlugadoFalse();
    List<Automovel> findByMarcaAndModeloAndAnoAndAlugadoFalse(String marca, String modelo, int ano);
}