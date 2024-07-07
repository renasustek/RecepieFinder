package com.github.renas.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renas.recipe.jackson.QuantityJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerialisationConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new QuantityJacksonModule());
        return mapper;
    }
}
