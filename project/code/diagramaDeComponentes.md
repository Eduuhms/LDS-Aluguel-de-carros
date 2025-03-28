```code

@startuml
title Diagrama de Componentes - Sistema de Aluguel de Automóveis

' === Componentes Principais ===
component "Navegador Web" as web

component "Frontend" as Frontend 

component "Backend (Java)" as Backend 

component "Banco de Dados (MySQL)" as Database 

component "Sistema de Gestão de Pedidos e Contratos" as GestaoPedidosContratos

component "Sistema de Gestão de Automóveis" as GestaoAutomoveis

component "Sistema de Gestão de Usuários" as GestaoUsuarios


' === Interfaces ===
interface "API" as api

' === Relações ===
web ..> Frontend : "Usa"

Frontend ..> api : "Requisições HTTP (JSON)"
api -- Backend

Backend ..> Database : "CRUD (MySQL)"

Backend ..> GestaoPedidosContratos : "Gerencia Pedidos e Contratos"
Backend ..> GestaoAutomoveis : "Gerencia Automóveis"
Backend ..> GestaoUsuarios : "Gerencia Usuários"

GestaoPedidosContratos ..> GestaoAutomoveis : "Relaciona Veículos aos Contratos"
GestaoPedidosContratos ..> GestaoUsuarios : "Relaciona Clientes e Agentes"
@enduml

```