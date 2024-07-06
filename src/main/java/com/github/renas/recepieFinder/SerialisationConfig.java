package com.github.renas.recepieFinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.renas.recepieFinder.serializerAndDeserializer.IngredientDeserializer;
import com.github.renas.recepieFinder.requestBodies.ingredient.Ingredient;
import com.github.renas.recepieFinder.serializerAndDeserializer.IngredientSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerialisationConfig {

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Ingredient.class, new IngredientSerializer());
        simpleModule.addDeserializer(Ingredient.class, new IngredientDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }
}
