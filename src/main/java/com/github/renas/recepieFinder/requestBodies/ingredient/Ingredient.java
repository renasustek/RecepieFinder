package com.github.renas.recepieFinder.requestBodies.ingredient;

import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;

public class Ingredient <Q extends Quantity<Q>>{
    String name;
    Quantity<Q> quantity;


    public Ingredient(Quantity<Q> quantity, String name) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quantity<Q> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<Q> quantity) {
        this.quantity = quantity;
    }
}
