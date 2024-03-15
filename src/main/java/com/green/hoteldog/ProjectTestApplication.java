package com.green.hoteldog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.green.hoteldog"})
@EnableScheduling
public class ProjectTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectTestApplication.class, args);
    }
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizerPageable(){
        return p -> p.setOneIndexedParameters(true);
    }
}
