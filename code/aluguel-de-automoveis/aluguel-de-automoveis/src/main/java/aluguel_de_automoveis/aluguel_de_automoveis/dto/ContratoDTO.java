package aluguel_de_automoveis.aluguel_de_automoveis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para criação e atualização de contratos")
public class ContratoDTO {
    @Schema(description = "Tipo do contrato (NORMAL ou CREDITO)", example = "NORMAL", allowableValues = {"NORMAL", "CREDITO"})
    private String tipo;
    
    @Schema(description = "Matrícula do automóvel a ser alugado", example = "ABC1234")
    private String automovelMatricula;
    
    @Schema(description = "ID do cliente que está alugando", example = "1")
    private Long clienteId;
    
    @Schema(description = "ID do proprietário do automóvel", example = "2")
    private Long proprietarioId;
    
    @Schema(description = "ID do pedido que originou este contrato (opcional)", example = "1")
    private Long pedidoId;
}
