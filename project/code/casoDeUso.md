```code

@startuml
left to right direction

:Usuario: as u
:Cliente: as c
:Agentes: as a
:Agente da Empresa: as ae
:Agente do Banco: as ab

rectangle "Sistema de Aluguel de Automóveis" {

        u --> (Modificar pedidos de aluguel)
        
        c --> (Introduzir pedidos de aluguel)
        c --> (Consultar pedidos de aluguel)
        c --> (Cancelar pedidos de aluguel)

        a --> (Analisar pedidos)


        (Analisar pedidos) --> (Aprovar Pedido)
        (Aprovar Pedido) --> (Executar Contrato)


        (Registrar Automóvel) as r
        (Registrar Automóvel como propriedade do cliente) .> r : include
        (Registrar Automóvel como propriedade do empresa) .> r : include
        (Registrar Automóvel como propriedade do banco) .> r : include
        ae --> r

        ae --> (Gerar Contrato)

}
        u <|-- c
        u <|-- a
        a <|-- ae
        a <|-- ab

@enduml

```