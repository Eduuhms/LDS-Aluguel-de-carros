package aluguel_de_automoveis.aluguel_de_automoveis.models;

import aluguel_de_automoveis.aluguel_de_automoveis.enums.TipoContrato;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TipoContrato tipo;
    
    @ManyToOne
    private Automovel automovel;
    
    @ManyToOne
    private Cliente cliente;
    
    @ManyToOne
    private Usuario proprietario;
    
    public void registrarPropriedade(Usuario usuario) {
        this.proprietario = usuario;
    }
}