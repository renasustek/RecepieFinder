package com.github.renas.recipe.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StructureIngredientsTest {

    @ParameterizedTest
    @MethodSource("provideIngredients")
    void whenGivenIngredientShouldReturnObject(String ingredientStr, Ingredient<Quantity> expectedIngredient) {
        Ingredient<Quantity> actualIngredient = StructureIngredients.stringToQuantity(ingredientStr);
        System.out.println(
                expectedIngredient.getName() + expectedIngredient.getQuantity().toString());
        System.out.println(actualIngredient.getName()
                + actualIngredient.getQuantity().toString().trim());
        assertThat(actualIngredient.getName())
                .isEqualTo(expectedIngredient.getName().trim());
        assertThat(actualIngredient.getQuantity().unit())
                .isEqualTo(expectedIngredient.getQuantity().unit());
        assertThat(actualIngredient.getQuantity().value())
                .isEqualTo(expectedIngredient.getQuantity().value());
    }

    private static Stream<Arguments> provideIngredients() {
        return Stream.of(
                Arguments.of(
                        "2 tbsp half-fat soured cream",
                        new Ingredient<>(new Volume(2, Unit.TABLESPOON), "half-fat soured cream")),
                Arguments.of(
                        "250g pouch ready-to-eat quinoa (we used Merchant Gourmet)",
                        new Ingredient<>(
                                new Mass(250, Unit.GRAM), "pouch ready-to-eat quinoa (we used Merchant Gourmet)")));
    }
}
