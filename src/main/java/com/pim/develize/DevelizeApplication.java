package com.pim.develize;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DevelizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevelizeApplication.class, args);
        System.out.println("Develize Started!");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
