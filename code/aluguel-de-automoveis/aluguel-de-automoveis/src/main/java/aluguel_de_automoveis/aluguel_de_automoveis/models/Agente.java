package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Agente extends Usuario {

    @Column(nullable = false, unique = true)
    private String cnpj;
}