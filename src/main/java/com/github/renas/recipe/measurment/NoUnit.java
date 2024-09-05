package com.github.renas.recipe.measurment;

public class NoUnit implements Quantity {

    double value;

    public NoUnit(double value) {
        this.value = value;
    }

    @Override
    public double value() {
        return value;
    }

    @Override
    public Unit unit() {
        return Unit.NOUNIT;
    }
}
