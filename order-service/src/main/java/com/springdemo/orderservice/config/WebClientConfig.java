package com.springdemo.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //In order to use Webclient we need spring-webflux dependency
    @Bean
    public WebClient webclient(){
        //bean will be created with the name webclient method
        return WebClient.builder().build();

    }

}
