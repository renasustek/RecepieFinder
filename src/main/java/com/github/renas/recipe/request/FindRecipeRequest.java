package com.github.renas.recipe.request;

import java.util.List;

public record FindRecipeRequest(
        List<String> mustIngredients, List<String> shouldIngredients, List<String> mustNotIngredients, int serves) {}
