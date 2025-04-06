package aluguel_de_automoveis.aluguel_de_automoveis.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Schema(description = "Entidade que representa um automóvel disponível para aluguel")
public class Automovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do automóvel", example = "1")
    private Long id;
    
    @Schema(description = "Matrícula do automóvel", example = "ABC1234")
    private String matricula;
    
    @Schema(description = "Ano de fabricação", example = "2023")
    private int ano;
    
    @Schema(description = "Marca do automóvel", example = "Toyota")
    private String marca;
    
    @Schema(description = "Modelo do automóvel", example = "Corolla")
    private String modelo;
    
    @Schema(description = "Placa do automóvel", example = "ABC1D23")
    private String placa;
    
    @Schema(description = "Indica se o automóvel está alugado", example = "false")
    private Boolean alugado = false;
}