package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bairro;
    private String rua;
    private String numero;
    private String cep;
}
