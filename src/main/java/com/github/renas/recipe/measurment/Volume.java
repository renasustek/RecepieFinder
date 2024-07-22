package com.github.renas.recipe.measurment;

public class Volume implements Quantity{

    public final int value;

    public final Unit unit;

    public Volume(int value, Unit unit){

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