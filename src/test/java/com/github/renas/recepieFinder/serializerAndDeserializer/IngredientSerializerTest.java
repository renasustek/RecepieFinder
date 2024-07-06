package com.github.renas.recepieFinder.serializerAndDeserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.renas.recepieFinder.requestBodies.ingredient.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class IngredientSerializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Ingredient.class, new IngredientSerializer());
        objectMapper.registerModule(module);
    }

    @Test
    void whenValidIngredientReturnsValidJson() throws JsonProcessingException {
        Quantity<Mass> quantity = Quantities.getQuantity(10, Units.GRAM);
        Ingredient ingredient = new Ingredient(quantity, "Sugar");//todo NEED TO GO OVER GENERICS!!!

        String json = objectMapper.writeValueAsString(ingredient);

        String expectedJson = "{\"quantity\":\"10\",\"unitOfMeasurement\":\"g\",\"name\":\"Sugar\"}";
        assertThat(json).isEqualTo(expectedJson);

    }

}
