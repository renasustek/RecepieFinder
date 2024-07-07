package com.github.renas.recipe.request.ingredient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.measure.Quantity;

public class Ingredient<Q extends Quantity<Q>> {
    String name;
    Quantity<Q> quantity;

    @JsonCreator
    public Ingredient(@JsonProperty("quantity") Quantity<Q> quantity, @JsonProperty("name") String name) {
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
