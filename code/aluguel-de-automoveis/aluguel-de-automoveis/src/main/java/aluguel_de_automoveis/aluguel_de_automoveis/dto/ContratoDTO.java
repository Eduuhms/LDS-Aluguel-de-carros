package aluguel_de_automoveis.aluguel_de_automoveis.dto;

import lombok.Data;

@Data
public class ContratoDTO {
    private String tipo;
    private String automovelMatricula;
    private Long clienteId;
    private Long proprietarioId;
}
