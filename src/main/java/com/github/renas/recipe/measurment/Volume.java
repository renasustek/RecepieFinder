package com.github.renas.recipe.measurment;

public class Volume implements Quantity{

    public final double value;

    public final Unit unit;

    public Volume(double value, Unit unit){

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
        return "Volume{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}