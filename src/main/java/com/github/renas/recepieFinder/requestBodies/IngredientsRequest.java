package com.github.renas.recepieFinder.requestBodies;

import java.util.List;

public record IngredientsRequest(
        List<String> mustIngredients, List<String> shouldIngredients, List<String> mustNotIngredients) {}
