package com.github.renas.recipe;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renas.recipe.jackson.QuantityJacksonModule;
import org.junit.jupiter.api.Test;

class SerialisationConfigTest {

    @Test
    void objectMapperShouldBeConfigured() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new QuantityJacksonModule());

        SerialisationConfig config = new SerialisationConfig();
        ObjectMapper beanMapper = config.getObjectMapper();

        assertNotNull(beanMapper, "ObjectMapper bean should not be null");

        assertTrue(
                beanMapper.getRegisteredModuleIds().contains(new QuantityJacksonModule().getModuleName()),
                "QuantityJacksonModule should be registered with ObjectMapper");
    }
}
