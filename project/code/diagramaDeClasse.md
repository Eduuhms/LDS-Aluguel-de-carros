```code

@startuml
skinparam classAttributeIconSize 0
enum TipoContrato {
    NORMAL,
    CRÉDITO
}

abstract "Usuario" {
    - usuario: String 
    - nome: String
    - email: String 
    - senha: String 
    + entrar(): void 
    + sair(): void 
    + modificarPedido(Pedido pedido): void
}

class "Cliente" {
    - rg: String 
    - cpf: String 
    - endereco: String 
    - profissao: String 
    - rendimentosAuferidos: List<Double> 
    - int MAX_RENDIMENTOS_AUFERIDOS
    - agentes: List<Agente> 
    + modificarPedido(Pedido pedido): void 
    + introduzirPedido(): void 
    + consultarPedido(Pedido pedido): Pedido 
    + cancelarPedido(Pedido pedido): void
}

abstract "Agente" {
    - cnpj: String 
    + analisarPedidoFinanceiramente(Pedido pedido): void 
}

class "Banco" {}

class "Empresa" {
    + avaliarPedido(Pedido pedido): void 
    + gerarContrato(Pedido pedido): void
}

class "Automovel" {
    - matricula: String
    - ano: int
    - marca: String
    - modelo: String
    - placa: String
    - alugado: Boolean
}

class "Contrato" {
    - tipo: TipoContrato
    - automovel : Automovel
    - cliente: Cliente
    - proprietario: Usuario
    + registrarPropriedade(Usuario usuario): void
}

class "Pedido" {
    - cliente: Cliente
    - veiculo : Veiculo
}

interface "Crud" {
    + apagar() : void
    + registrar() : void
    + editar() : void
    + pesquisar() : void
    + preencher() : void
}

"Usuario" <|-- "Cliente"
"Usuario" <|-- "Agente"
"Agente" <|-- "Banco"
"Agente" <|-- "Empresa"

"Cliente" -- "Pedido"
"Automovel" -- "Pedido"

"Contrato" -- "Usuario"
"Contrato" -- "Automovel"
"Contrato" -- "Cliente"

"Usuario" <|.. "Crud"
"Contrato" <|.. "Crud"
"Pedido" <|.. "Crud"
"Automovel" <|.. "Crud"

@enduml

```