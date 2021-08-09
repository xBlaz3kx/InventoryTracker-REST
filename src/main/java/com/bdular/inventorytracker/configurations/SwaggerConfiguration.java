package com.bdular.inventorytracker.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bdular.inventorytracker.api"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public SecurityConfiguration swaggerSecurity() {
        return SecurityConfigurationBuilder.builder()
                .clientId("admin")
                .clientSecret("admin")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

}
