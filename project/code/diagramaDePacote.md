```code

@startuml
package "Usuarios" as usuarios {
    class Usuario
    class Cliente
    class Agente
}

package "AgentesFinanceiros" as financeiro {
    class Banco
    class Empresa
}

package "Transacoes" as transacoes {
    class Pedido
    class Automovel
}

package "Contratos" as contratos {
    class Contrato
    enum TipoContrato
}

package "Operacoes CRUD" as crud {
    interface Crud
}

usuarios -[hidden]> financeiro
usuarios -[hidden]> transacoes
transacoes -[hidden]> contratos
contratos -[hidden]> usuarios
contratos -[hidden]> financeiro
crud <|.. usuarios
crud <|.. contratos
crud <|.. transacoes

@enduml

```