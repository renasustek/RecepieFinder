package com.github.renas.recipe.measurment;

public class NoUnit implements Quantity {

    double value;

    public NoUnit(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public Unit getUnit() {
        return Unit.NOUNIT;
    }
}
