package aluguel_de_automoveis.aluguel_de_automoveis.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    public static final int MAX_RENDIMENTOS_AUFERIDOS = 3;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String rg;

    private String profissao;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Rendimento> rendimentosAuferidos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Agente> agentes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    public void modificarPedido(Pedido pedido) {
        // Implementação específica para cliente
    }

    public void introduzirPedido() {
        // Lógica para criar um novo pedido
    }

    public Pedido consultarPedido(Pedido pedido) {
        // Lógica para consultar um pedido
        return pedido;
    }

    public void cancelarPedido(Pedido pedido) {
        // Lógica para cancelar um pedido
    }
}