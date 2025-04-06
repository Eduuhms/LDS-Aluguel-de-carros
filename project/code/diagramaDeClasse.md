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
    + registrarPropriedade(Usuario usuario): void
}

class "Pedido" {
}

class "Empregador" {
    - cnpj: String
    - nome: String
    - setor : String
}

class "Rendimento" {
    - valor: Double
}

class "Endereco"{
    - bairro : String
    - rua : String
    - numero : Integer
    - cep : String
}

"Usuario" <|-- "Cliente"
"Usuario" <|-- "Agente"
"Agente" <|-- "Banco"
"Agente" <|-- "Empresa"

"Cliente" "1" -- "*" "Endereco"
"Cliente" "1" -- "*" "Rendimento"
"Cliente" "1" -- "*" "Pedido"
"Cliente" -- "*" "Agente" : "agentes\nassociados"

"Rendimento" "*" -- "1" "Empregador"
"Pedido" "*" -- "1" "Automovel"
"Pedido" "1" -- "1" "Contrato"

"Contrato" "*" -- "1" "Automovel"
"Contrato" "*" -- "1" "Cliente"
"Contrato" "*" -- "1" "Usuario" : "proprietário"
@enduml



```