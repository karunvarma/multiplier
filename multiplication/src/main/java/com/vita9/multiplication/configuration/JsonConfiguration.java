package com.vita9.multiplication.configuration;


import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.Module;

@Configuration
public class JsonConfiguration {

    @Bean
    public Module hibernateModule(){
        return new Hibernate5Module();
    }
}
