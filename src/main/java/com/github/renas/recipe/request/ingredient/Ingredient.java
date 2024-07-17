package com.github.renas.recipe.request.ingredient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.renas.recipe.measurment.Quantity;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Ingredient<T extends Quantity<T>> {
    @Field(type = FieldType.Text)
    String name;

    @Field(type = FieldType.Nested, includeInParent = true)
    Quantity<T> quantity;

    @JsonCreator
    public Ingredient(@JsonProperty("quantity") Quantity<T> quantity, @JsonProperty("name") String name) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
