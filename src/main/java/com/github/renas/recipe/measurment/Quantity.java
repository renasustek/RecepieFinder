package com.github.renas.recipe.measurment;

public interface Quantity<Q extends Quantity<Q>> {
    int getValue();

    Unit getUnit();

}
