package aluguel_de_automoveis.aluguel_de_automoveis.models;

import aluguel_de_automoveis.aluguel_de_automoveis.enums.TipoContrato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
@Schema(description = "Entidade que representa um contrato de aluguel de automóvel")
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do contrato", example = "1")
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo do contrato (NORMAL ou CREDITO)", example = "NORMAL", allowableValues = {"NORMAL", "CREDITO"})
    private TipoContrato tipo;
    
    @ManyToOne
    @Schema(description = "Automóvel associado ao contrato")
    private Automovel automovel;
    
    @ManyToOne
    @Schema(description = "Cliente associado ao contrato")
    private Cliente cliente;
    
    @ManyToOne
    @Schema(description = "Proprietário do automóvel")
    private Usuario proprietario;
    
    @OneToOne
    @Schema(description = "Pedido que originou o contrato")
    private Pedido pedido;
    
    public void registrarPropriedade(Usuario usuario) {
        this.proprietario = usuario;
    }
}