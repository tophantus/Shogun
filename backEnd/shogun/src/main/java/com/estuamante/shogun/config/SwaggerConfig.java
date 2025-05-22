package com.estuamante.shogun.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Shogun API's")
                        .description("Shogun E-commerce Application APIs")
                        .version("1.0")
                        .contact(new Contact()
                                .name("EstuAmante")));
    }
}
