package com.github.renas.recepieFinder.requestBodies;

import java.util.List;

public record FindRecipeRequest(
        List<String> mustIngredients, List<String> shouldIngredients, List<String> mustNotIngredients) {}
