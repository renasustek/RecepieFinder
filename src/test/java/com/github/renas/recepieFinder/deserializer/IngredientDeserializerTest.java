package com.github.renas.recepieFinder.deserializer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.renas.recepieFinder.requestBodies.ingredient.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class IngredientDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Ingredient.class, new IngredientDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    public void whenGivenProperJsonAssertsCorrect() throws Exception {
        String json = "{ \"quantity\": \"1\", \"unitOfMeasurement\": \"gram\", \"name\": \"one\" }";

        Ingredient ingredient = objectMapper.readValue(json, Ingredient.class);
        assertThat(ingredient).isNotNull();
        assertThat(Quantities.getQuantity(1, Units.GRAM)).isEqualTo(ingredient.getQuantity());
        assertThat("one").isEqualTo(ingredient.getName());
    }

    @Test
    public void whenGivenImproperJsonAssertsIncorrect() throws Exception {
        String json = "{ \"quantity\": \"1\", \"unitOfMeasurement\": \"gramsss\", \"name\": \"one\" }";
        assertThatThrownBy(() -> {
            objectMapper.readValue(json, Ingredient.class);
        }).isInstanceOf(JsonMappingException.class)
                .hasMessageContaining("error deserializing unit");
    }
}
