```code

@startuml
title Diagrama de Componentes - Sistema de Aluguel de Automóveis (Modificado)

' === Componentes Principais ===
component "Navegador Web" as web

component "Frontend" as Frontend 

component "Backend (Java)" as Backend {
    component "Sistema de Gestão de Pedidos" as GestaoPedidos
    component "Sistema de Gestão de Contratos" as GestaoContratos
    component "Sistema de Gestão de Automóveis" as GestaoAutomoveis
    component "Sistema de Gestão de Usuários" as GestaoUsuarios
}

component "Banco de Dados (MySQL)" as Database 

' === Interfaces ===
interface "API" as api

' === Relações ===
web ..> Frontend : "Usa"

Frontend ..> api : "Requisições HTTP (JSON)"
api -- Backend

Backend ..> Database : "CRUD (MySQL)"

' Relações entre os subsistemas
GestaoPedidos ..> GestaoContratos : "Gera"
GestaoContratos ..> GestaoAutomoveis : "Vincula veículos"
GestaoContratos ..> GestaoUsuarios : "Vincula clientes/agentes"
GestaoPedidos ..> GestaoUsuarios : "Verifica permissões"
@enduml

```