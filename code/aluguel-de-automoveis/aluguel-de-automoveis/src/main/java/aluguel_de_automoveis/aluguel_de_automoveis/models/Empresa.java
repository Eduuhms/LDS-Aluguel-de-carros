package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Empresa extends Agente {
    
    public void avaliarPedido(Pedido pedido) {
        // Lógica para avaliação de pedido pela empresa
    }
    
    public void gerarContrato(Pedido pedido) {
        // Lógica para gerar contrato com base no pedido
    }
}