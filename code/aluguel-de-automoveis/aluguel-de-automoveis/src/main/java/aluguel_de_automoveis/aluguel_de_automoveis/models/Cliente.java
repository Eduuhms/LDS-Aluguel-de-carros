package aluguel_de_automoveis.aluguel_de_automoveis.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import aluguel_de_automoveis.aluguel_de_automoveis.models.Endereco;
import aluguel_de_automoveis.aluguel_de_automoveis.models.Rendimento;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Rendimento> rendimentos;
}
