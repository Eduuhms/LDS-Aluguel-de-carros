package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "automoveis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Automovel {

    @Id
    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private boolean alugado = false;
}