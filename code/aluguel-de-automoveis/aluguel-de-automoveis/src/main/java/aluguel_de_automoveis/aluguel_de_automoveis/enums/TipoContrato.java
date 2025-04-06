package aluguel_de_automoveis.aluguel_de_automoveis.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de contrato disponíveis")
public enum TipoContrato {
    @Schema(description = "Contrato de aluguel normal (curto prazo)")
    NORMAL,
    
    @Schema(description = "Contrato de aluguel com financiamento (longo prazo)")
    CREDITO
}