package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://9088.408procr.amypo.ai/");
        server.setDescription("College portal preview URL");

        return new OpenAPI()
                .addServersItem(server)
                .info(new Info()
                        .title("Demo Application API")
                        .version("1.0")
                        .description("Swagger UI for Demo Application"));
    }
}
