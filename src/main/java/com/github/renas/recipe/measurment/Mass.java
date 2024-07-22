package com.github.renas.recipe.measurment;

public class Mass implements Quantity {

    public final int value;

    public final Unit unit;

    public Mass(int value, Unit unit){

        this.value = value;
        this.unit = unit;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }
}
