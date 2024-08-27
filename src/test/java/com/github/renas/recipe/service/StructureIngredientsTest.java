package com.github.renas.recipe.service;

import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.request.ingredient.Ingredient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StructureIngredientsTest {

    @ParameterizedTest
    @MethodSource("provideIngredients")
    void whenGivenIngredientShouldReturnObject(String ingredientStr, Ingredient<Volume> expectedIngredient) {
        Ingredient<Quantity> actualIngredient = StructureIngredients.stringToQuantity(ingredientStr);
        System.out.println(expectedIngredient.getName() + expectedIngredient.getQuantity().toString());
        System.out.println(actualIngredient.getName() + actualIngredient.getQuantity().toString());
        assertThat(actualIngredient.getName())
                .isEqualTo(expectedIngredient.getName());
        assertThat(actualIngredient.getQuantity().getUnit())
                .isEqualTo(expectedIngredient.getQuantity().getUnit());
        assertThat(actualIngredient.getQuantity().getValue())
                .isEqualTo(expectedIngredient.getQuantity().getValue());
    }

    private static Stream<Arguments> provideIngredients() {
        return Stream.of(
                Arguments.of(
                        "2 tbsp half-fat soured cream",
                        new Ingredient<>(new Volume(2, Unit.TABLESPOON), "half-fat soured cream")),
                Arguments.of(
                        "250g pouch ready-to-eat quinoa (we used Merchant Gourmet)",
                        new Ingredient<>(
                                new Mass(250, Unit.GRAM), "pouch ready-to-eat quinoa (we used Merchant Gourmet)")),
                Arguments.of("green salad, to serve", new Ingredient<>(null, "green salad, to serve")));
    }
}
