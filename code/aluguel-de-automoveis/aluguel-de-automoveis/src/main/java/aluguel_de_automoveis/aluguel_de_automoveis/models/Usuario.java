package aluguel_de_automoveis.aluguel_de_automoveis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String usuario;
    
    private String nome;
    private String email;
    private String senha;
    
    public void entrar() {
        // Lógica para autenticação de usuário
    }
    
    public void sair() {
        // Lógica para logout de usuário
    }
    
    public void modificarPedido(Pedido pedido) {
        // Lógica para modificar um pedido
    }
}