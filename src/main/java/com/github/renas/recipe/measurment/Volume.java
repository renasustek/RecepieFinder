package com.github.renas.recipe.measurment;

public record Volume(double value, Unit unit) implements Quantity {

    @Override
    public String toString() {
        return "Volume{" + "value=" + value + ", unit=" + unit + '}';
    }
}
