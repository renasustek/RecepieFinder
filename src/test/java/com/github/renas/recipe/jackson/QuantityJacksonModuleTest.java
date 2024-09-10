package com.github.renas.recipe.jackson;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantityJacksonModuleTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new QuantityJacksonModule();
        objectMapper.registerModule(module);
    }

    @Test
    void deserializeUnitSuccessfully() throws IOException {
        String json = "\"kilogram\"";
        Unit unit = objectMapper.readValue(json, Unit.class);
        assertEquals(Unit.KILOGRAM, unit);

        json = "\"tbsp\"";
        unit = objectMapper.readValue(json, Unit.class);
        assertEquals(Unit.TABLESPOON, unit);

        json = "\"litres\"";
        unit = objectMapper.readValue(json, Unit.class);
        assertEquals(Unit.LITER, unit);
    }

    @Test
    void serializeMassKilogramSuccessfully() throws IOException {
        Quantity quantity = new Mass(5, Unit.KILOGRAM);
        String expectedJson = "{\"quantity\":5.0,\"unit\":\"KILOGRAM\"}";
        String jsonResult = objectMapper.writeValueAsString(quantity);
        assertEquals(expectedJson, jsonResult);
    }

    @Test
    void serializeMassgramSuccessfully() throws IOException {
        Quantity quantity = new Mass(5, Unit.GRAM);
        String expectedJson = "{\"quantity\":5.0,\"unit\":\"GRAM\"}";
        String jsonResult = objectMapper.writeValueAsString(quantity);
        assertEquals(expectedJson, jsonResult);
    }

    @Test
    void serializeVolumeMlSuccessfully() throws IOException {
        Quantity quantity = new Volume(5, Unit.MILLILITER);
        String expectedJson = "{\"quantity\":5.0,\"unit\":\"MILLILITER\"}";
        String jsonResult = objectMapper.writeValueAsString(quantity);
        assertEquals(expectedJson, jsonResult);
    }

    @Test
    void serializeVolumeTspSuccessfully() throws IOException {
        Quantity quantity = new Volume(5, Unit.TEASPOON);
        String expectedJson = "{\"quantity\":5.0,\"unit\":\"TEASPOON\"}";
        String jsonResult = objectMapper.writeValueAsString(quantity);
        assertEquals(expectedJson, jsonResult);
    }

    @Test
    void deserializeQuantityInvalidUnitThrowsException() {
        String invalidJson = "{\"quantity\":5,\"unit\":\"invalidUnit\"}";
        assertThrows(JsonParseException.class, () -> objectMapper.readValue(invalidJson, Quantity.class));
    }
}
