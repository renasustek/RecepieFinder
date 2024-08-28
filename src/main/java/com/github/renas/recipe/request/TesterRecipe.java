package com.github.renas.recipe.request;

import java.util.List;

public record TesterRecipe(
        String name, String description, List<String> ingredients, List<String> steps, String serves) {}
