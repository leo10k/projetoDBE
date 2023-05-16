package br.com.fiap.projetodbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class DocumentationConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("projetoDBE API")
                    .description("Api for a gaming site where users can share links, text posts, images and videos about the games they play or are interested in, using Spring Boot, Spring Security and H2 Database")
                    .version("V1")
                    .contact(new Contact()
                        .name("Leonardo Silva")
                        .email("leosilvamacedo@outlook.com.br")
                    )
                    .license(new License()
                        .name("MIT Open Soucer")
                        .url("http://projetoDBE.com/licenca")
                    )
                )
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                        .bearerFormat("JWT")));
    }

}
