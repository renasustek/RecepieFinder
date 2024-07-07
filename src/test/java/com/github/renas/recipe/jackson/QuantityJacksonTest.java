package com.github.renas.recipe.jackson;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renas.recipe.request.ingredient.Ingredient;
import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

@SpringBootTest
class QuantityJacksonTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new QuantityJacksonModule());
    }

    @Test
    void whenGivenProperJsonAssertsCorrect() throws Exception {
        String json =
                """
                {
                    "quantity" : {
                        "value": "1",
                         "unit" : "gram"
                    },
                    "name": "one"
                }""";

        Ingredient<Mass> ingredient = objectMapper.readValue(json, Ingredient.class);
        assertThat(ingredient).isNotNull();
        assertThat(Quantities.getQuantity(1, Units.GRAM)).isEqualTo(ingredient.getQuantity());
        assertThat("one").isEqualTo(ingredient.getName());
    }

    @Test
    void whenGivenImproperJsonAssertsIncorrect() {
        String json =
                """
                {
                "quantity" : {
                    "value": "1",
                     "unit" : "gramsss"
                },
                "name": "one"
                }""";
        assertThatThrownBy(() -> {
                    objectMapper.readValue(json, Ingredient.class);
                })
                .isInstanceOf(JsonMappingException.class)
                .hasMessageContaining("error deserializing unit");
    }

    @Test
    void whenValidIngredientReturnsValidJson() throws JsonProcessingException {
        Quantity<Mass> quantity = Quantities.getQuantity(10, Units.GRAM);
        Ingredient<Mass> ingredient = new Ingredient<>(quantity, "Sugar");

        String json = objectMapper.writeValueAsString(ingredient);

        String expectedJson = "{\"quantity\":{\"quantity\":\"10\",\"unit\":\"g\"},\"name\":\"Sugar\"}";
        assertThat(json).isEqualTo(expectedJson);
    }
}
