package com.github.renas.recipe.measurment;

public record Mass(double value, Unit unit) implements Quantity {

    @Override
    public String toString() {
        return "Mass{" + "value=" + value + ", unit=" + unit + '}';
    }
}
