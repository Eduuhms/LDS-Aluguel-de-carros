package aluguel_de_automoveis.aluguel_de_automoveis.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empregadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empregador {

    @Id
    @Column(unique = true, nullable = false)
    private String cnpj;

    private String nome;
    private String setor;
}
