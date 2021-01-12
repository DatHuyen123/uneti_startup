package com.server.traffic.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapperConfig : config model mapper to bean container
 *
 * @author DatDV
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}
}
