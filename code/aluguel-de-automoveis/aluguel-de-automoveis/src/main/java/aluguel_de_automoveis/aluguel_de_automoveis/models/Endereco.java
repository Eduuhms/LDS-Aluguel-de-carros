package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    private String clienteCpf; // Mesmo ID do Cliente

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false, length = 9)
    private String cep;

    @MapsId // Indica que o ID será mapeado a partir do Cliente
    @OneToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf")
    private Cliente cliente;
}
