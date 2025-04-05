package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aluguel_de_automoveis.aluguel_de_automoveis.enums.TipoContrato;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Cliente;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByCliente(Cliente cliente);
    List<Contrato> findByTipo(TipoContrato tipo);
}