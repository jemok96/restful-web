package com.example.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * API Documentation 제공
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*
    http://localhost:8080/swagger-ui/index.html
    http://localhost:8080/v2/api-docs
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
