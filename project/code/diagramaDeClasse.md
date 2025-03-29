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
    - profissao: String 
    - rendimentosAuferidos: List<Rendimento> 
    - MAX_RENDIMENTOS_AUFERIDOS : const int = 3
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

class "Empregador" {
    - cnpj: String
    - nome: String
    - setor : String
}

class "Rendimento" {
    - valor: Double
    - cliente : Cliente
    - empregador: Empregador
}

interface "Crud" {
    + apagar() : void
    + registrar() : void
    + editar() : void
    + pesquisar() : void
    + preencher() : void
}

class "Endereco"{
    - cliente: Cliente
    - bairro : String
    - rua : String
    - numero : Integer
    - cep : String
}

"Usuario" <|-- "Cliente"
"Usuario" <|-- "Agente"
"Agente" <|-- "Banco"
"Agente" <|-- "Empresa"

"Cliente" -- "Pedido"
"Cliente" -- "Endereco"
"Automovel" -- "Pedido"

"Contrato" -- "Usuario"
"Contrato" -- "Automovel"
"Contrato" -- "Cliente"

"Cliente" -- "Rendimento"
"Empregador" -- "Rendimento"

"Usuario" <|.. "Crud"
"Contrato" <|.. "Crud"
"Pedido" <|.. "Crud"
"Automovel" <|.. "Crud"

@enduml


```