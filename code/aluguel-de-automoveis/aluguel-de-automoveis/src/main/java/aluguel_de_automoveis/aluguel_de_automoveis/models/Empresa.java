package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @Column(unique = true, nullable = false)
    private String cnpj;

    private String nome;
    private String setor;
}
