package com.github.renas.recepieFinder.requestBodies;

import java.util.List;

public record Recipe(String name, List<String> ingredients, List<String> steps) {
}
