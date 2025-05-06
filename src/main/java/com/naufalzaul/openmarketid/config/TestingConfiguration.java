package com.naufalzaul.openmarketid.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestingConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Assessment Test API").version("v1"))
                .addSecurityItem(new SecurityRequirement().addList("bearerToken"))
                .components(new Components()
                                    .addSecuritySchemes(
                                            "bearerToken", new SecurityScheme()
                                                    .name("bearerToken")
                                                    .type(SecurityScheme.Type.HTTP)
                                                    .scheme("bearer")
                                                    .bearerFormat("JWT")
                                    )
                );
    }
}
