package com.example.demo.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Micro-Learning Content Recommendation API")
                .description("REST API for micro-learning content and personalized recommendations")
                .version("1.0.0")
            )
            .addSecurityItem(new SecurityRequirement().addList("JWT"))
            .components(new Components()
                .addSecuritySchemes("JWT",
                    new SecurityScheme()
                        .name("JWT")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
