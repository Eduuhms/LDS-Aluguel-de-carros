package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Automovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String matricula;
    private int ano;
    private String marca;
    private String modelo;
    private String placa;
    private Boolean alugado = false;
}