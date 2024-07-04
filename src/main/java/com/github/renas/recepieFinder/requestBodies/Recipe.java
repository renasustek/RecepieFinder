package com.github.renas.recepieFinder.requestBodies;

import com.github.renas.recepieFinder.requestBodies.ingredient.Ingredient;

import java.util.List;

public record Recipe(String name, String description, List<Ingredient<?>> ingredients, List<String> steps, String serves) {}
