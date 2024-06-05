package com.github.renas.recepieFinder.other;

import java.util.List;

public record Recipe(String name, List<String> ingredients, String steps) {
}
