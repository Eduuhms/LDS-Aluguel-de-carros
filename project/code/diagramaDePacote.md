```code

@startuml
package "Sistema de Aluguel" {
    package "Modelo" {
        class Pedido
        class Contrato
        class Automovel
        abstract Usuario
        class Cliente
        abstract Agente
        class Banco
        class Empresa
    }
    
    package "Visao" {
        class VisaoPedido
        class VisaoContrato
        class VisaoAutomovel
        class VisaoUsuario
    }
    
    package "Controle" {
        class ControlePedido
        class ControleContrato
        class ControleAutomovel
        class ControleUsuario
    }
    
}

Usuario <|-- Cliente
Usuario <|-- Agente
Agente <|-- Banco
Agente <|-- Empresa

Cliente "1" -- "0..*" Pedido
Pedido "1" -- "1" Automovel
Automovel "*" -- "1" Usuario
Contrato "1" -- "0..1" Agente
Contrato "1" -- "1" Pedido
Banco "0..*" -- "1" Cliente


Modelo -- Controle
Controle -- Visao
Visao -- Modelo

ControlePedido -- Pedido
ControleContrato -- Contrato
ControleAutomovel -- Automovel
ControleUsuario -- Usuario

VisaoPedido -- Pedido
VisaoContrato -- Contrato
VisaoAutomovel -- Automovel
VisaoUsuario -- Usuario
@enduml

@enduml

```