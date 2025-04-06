package aluguel_de_automoveis.aluguel_de_automoveis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Aluguel de Automóveis")
                        .description("Sistema de gerenciamento de aluguel de automóveis - Permite cadastrar clientes, " +
                                "agentes, automóveis, gerenciar pedidos e criar contratos."));
    }
} 