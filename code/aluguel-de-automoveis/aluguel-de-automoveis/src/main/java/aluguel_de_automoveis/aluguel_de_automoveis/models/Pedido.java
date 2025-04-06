package aluguel_de_automoveis.aluguel_de_automoveis.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
@Schema(description = "Entidade que representa um pedido de aluguel de automóvel")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do pedido", example = "1")
    private Long id;
    
    @ManyToOne
    @Schema(description = "Cliente que realizou o pedido")
    private Cliente cliente;
    
    @ManyToOne
    @Schema(description = "Veículo solicitado no pedido")
    private Automovel veiculo;
    
    @OneToOne(mappedBy = "pedido")
    @Schema(description = "Contrato gerado a partir do pedido")
    private Contrato contrato;
    
    @Schema(description = "Indica se o pedido foi aprovado", example = "false")
    private boolean aprovado = false;
}