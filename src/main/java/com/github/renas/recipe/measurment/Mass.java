package com.github.renas.recipe.measurment;

public class Mass implements Quantity {

    public final double value;

    public final Unit unit;

    public Mass(double value, Unit unit) {

        this.value = value;
        this.unit = unit;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "Mass{" + "value=" + value + ", unit=" + unit + '}';
    }
}
