package com.github.renas.recepieFinder.requestBodies;

import java.util.List;

public record Recipe(String name,String description, List<String> ingredients, List<String> steps, String serves) {}
