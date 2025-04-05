```code

@startuml
node "Computador do usuário" as user_computer {
  component "Navegador" as browser
}

node "Servidor de aplicação" as app_server {
  component "Frontend" as frontend
  component "Banco de Dados" as database
}

database "Database" as db_storage

user_computer --> browser
browser -- app_server : HTTPS
frontend --> database
database --> db_storage
@enduml

```