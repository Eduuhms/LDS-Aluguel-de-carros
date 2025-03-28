package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rendimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empresa_cnpj", referencedColumnName = "cnpj")
    private Empresa empresa;

    private Double valor;
}
