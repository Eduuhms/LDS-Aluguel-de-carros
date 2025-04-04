package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
