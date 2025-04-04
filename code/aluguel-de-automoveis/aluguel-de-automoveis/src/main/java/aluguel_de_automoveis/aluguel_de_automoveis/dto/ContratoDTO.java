package aluguel_de_automoveis.aluguel_de_automoveis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratoDTO {
    private String tipo;
    private String automovelMatricula;
    private Long clienteId;
    private Long proprietarioId;
}
