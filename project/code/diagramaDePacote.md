```code

@startuml
package configs {}
package controllers {
    class ControlePedido
    class ControleContrato
    class ControleAutomovel
    class ControleUsuario
    class ControleCliente
    class ControleAgente
    class ControleRendimento
}

package models {
    class Pedido
    class Contrato
    class Automovel
    abstract Usuario
    class Cliente
    abstract Agente
    class Banco
    class Empresa
    class Empregador
    class Rendimento
    class Endereco
}

package repositories {
    class PedidoRepository
    class ContratoRepository
    class AutomovelRepository
    class UsuarioRepository
    class ClienteRepository
    class AgenteRepository
    class RendimentoRepository
}

package enumerators {
    enum TipoContrato
}

package views {
    class VisaoPedido
    class VisaoContrato
    class VisaoAutomovel
    class VisaoUsuario
    class VisaoCliente
    class VisaoAgente
    class VisaoRendimento
}

package dto {
    package aluguel {
        class PedidoDTO
        class ContratoDTO
    }
    
    package automovel {
        class AutomovelDTO
    }
    
    package cliente {
        class ClienteDTO
        class EnderecoDTO
    }
    
    package usuario {
        class UsuarioDTO
    }
    
    package banco {
        class BancoDTO
    }
}

' Relacionamentos entre pacotes
controllers ..> configs
controllers ..> repositories
controllers ..> models
controllers ..> dto
controllers ..> views
models ..> enumerators

' Relacionamentos de herança
Usuario <|-- Cliente
Usuario <|-- Agente
Agente <|-- Banco
Agente <|-- Empresa

' Relacionamentos entre classes
Cliente "1" -- "0..*" Pedido
Pedido "1" -- "1" Automovel
Contrato "1" -- "1" Automovel
Contrato "1" -- "1" Cliente
Contrato "1" -- "1" Usuario
Cliente "1" -- "1" Endereco
Cliente "1" -- "0..*" Rendimento
Rendimento "1" -- "1" Empregador
Banco "0..*" -- "1" Cliente

' Relacionamentos repositories
repositories ..> models

' Relacionamentos views
views ..> dto
views ..> models

' Relacionamentos DTO
dto ..> models
@enduml

```