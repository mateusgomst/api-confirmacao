package com.aba_mais.api_confirmacao.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ABA+ Agendamentos API")
                        .description("API para gerenciar confirmação de agendamentos")
                        .version("1.0.0"));
    }
}