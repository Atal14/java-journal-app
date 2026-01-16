package com.edigest.atal.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.lang.Arrays;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {
        Server localServer = new Server().url("http://localhost:8080").description("local");
        Server liveServer = new Server().url("http://localhost:8081").description("live");

        Tag publicApis = new Tag().name("Public APIs");
        return new OpenAPI()
                .info(
                        new Info().title("Journal App APIs")
                                .description("By Vipul"))
                .servers(Arrays.asList(new Server[]{localServer, liveServer}
                        ))
                .tags(Arrays.asList(new Tag[]{publicApis}))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}
