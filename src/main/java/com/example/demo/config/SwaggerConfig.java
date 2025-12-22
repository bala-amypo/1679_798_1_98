package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        Server server = new Server();
        server.setUrl("https://9088.408procr.amypo.ai/");
                .info(new Info()
                        .title("Demo Application API")
                        .version("1.0")
                        .description("Swagger UI for Demo Application (No Security, No Auth)"));
    }
}
