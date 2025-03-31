@startuml
package configs {}
package controllers {
}

package models {
}

package repositories {
}

package enumerators {
}

package views {
}

package dto {
}

' Relacionamentos entre pacotes
controllers ..> configs
controllers ..> repositories
controllers ..> models
controllers ..> dto
controllers ..> views
models ..> enumerators


' Relacionamentos repositories
repositories ..> models

' Relacionamentos views
views ..> dto
views ..> models

' Relacionamentos DTO
dto ..> models
@enduml