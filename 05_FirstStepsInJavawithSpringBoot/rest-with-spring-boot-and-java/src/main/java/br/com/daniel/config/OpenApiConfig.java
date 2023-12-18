package br.com.daniel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("RESTfull API witch Java and Spring Boot")
				.version("v1")
				.description("Some description about your API.")
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache").url("http://springdoc.org")));
	}

}
