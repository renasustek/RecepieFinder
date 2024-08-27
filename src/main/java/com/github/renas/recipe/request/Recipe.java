package com.github.renas.recipe.request;

import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.request.ingredient.Ingredient;
import java.util.List;

public record Recipe(
        String name,
        String description,
        List<Ingredient<Quantity>> ingredients,
        List<String> steps,
        String serves)
{}
