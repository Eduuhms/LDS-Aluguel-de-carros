package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Agente extends Usuario {
    
    private String cnpj;
    
    public void analisarPedidoFinanceiramente(Pedido pedido) {
        // Lógica para análise financeira do pedido
    }
}