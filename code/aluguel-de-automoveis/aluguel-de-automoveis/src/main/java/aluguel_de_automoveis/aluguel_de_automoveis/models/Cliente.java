package aluguel_de_automoveis.aluguel_de_automoveis.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(unique = true, nullable = false)
    private String cpf; // CPF é a chave primária

    @Column(unique = true, nullable = false)
    private String rg;

    @Column(nullable = false)
    private String nome;

    private String profissao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Rendimento> rendimentos;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Endereco endereco;
}
