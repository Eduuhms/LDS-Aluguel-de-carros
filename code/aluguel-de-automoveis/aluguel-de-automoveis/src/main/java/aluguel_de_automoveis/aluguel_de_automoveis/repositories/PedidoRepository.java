package aluguel_de_automoveis.aluguel_de_automoveis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}