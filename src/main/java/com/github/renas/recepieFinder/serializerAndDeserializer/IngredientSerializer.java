package com.github.renas.recepieFinder.serializerAndDeserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.renas.recepieFinder.requestBodies.ingredient.Ingredient;

import javax.measure.Quantity;
import java.io.IOException;

public class IngredientSerializer extends JsonSerializer<Ingredient> {

    @Override
    public void serialize(Ingredient ingredient, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        Quantity<?> quantity = ingredient.getQuantity();
        gen.writeStringField("quantity", quantity.getValue().toString());
        gen.writeStringField("unitOfMeasurement", quantity.getUnit().toString());
        gen.writeStringField("name", ingredient.getName());
        gen.writeEndObject();
    }
}