package com.github.renas.recipe.measurment;

public enum Unit {
    GRAM("gram"),
    KILOGRAM("kilogram"),
    MILLILITER("milliliter"),
    LITER("liter"),
    TABLESPOON("tablespoon"),
    TEASPOON("teaspoon"),
    NOUNIT("n/a");
    private final String name;

    Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
