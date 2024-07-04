package com.github.renas.recepieFinder.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.renas.recepieFinder.requestBodies.ingredient.Ingredient;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Unit;
import java.io.IOException;

public class IngredientDeserializer extends JsonDeserializer<Ingredient> {
    @Override
    public Ingredient deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        int quantity = node.get("quantity").asInt();
        String unitOfMeasurement = node.get("unitOfMeasurement").asText().trim();
        String name = node.get("name").asText();


        return new Ingredient(Quantities.getQuantity(quantity, getUnit(unitOfMeasurement)), name);
    }


    private static Unit<?> getUnit(String unitString) {
        return switch (unitString.toLowerCase()) {
            case "g", "gram", "grams" -> Units.GRAM;
            case "kg", "kilogram", "kilograms" -> Units.KILOGRAM;
            case "ml", "millilitre", "millilitres" -> Units.LITRE.divide(1000);
            case "l", "litre", "litres" -> Units.LITRE;
            case "tbsp", "tablespoon", "tablespoons" ->
                    Units.LITRE.multiply(((double) 15 / 1000)); // Example: 1 tbsp = 15 ml
            default -> null;
        };
    }
}
