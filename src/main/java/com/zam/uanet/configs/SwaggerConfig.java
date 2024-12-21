package com.zam.uanet.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SwaggerConfig {
/*
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API REST")
                        .description("ZAM MARKET API DOCUMENTATION")
                        .version("1.0")
                        .contact(new Contact().email("21jose03.free@gmail.com")
                                .name("UA NET"))
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }*/

}
