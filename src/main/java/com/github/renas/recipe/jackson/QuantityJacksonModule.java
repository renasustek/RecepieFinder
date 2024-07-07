package com.github.renas.recipe.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.io.IOException;
import java.io.Serial;

public class QuantityJacksonModule extends SimpleModule {
    public QuantityJacksonModule() {
        addSerializer(Quantity.class, new QuantitySerializer());
        addDeserializer(Quantity.class, new QuantityDeserializer());
        addDeserializer(Unit.class, new UnitJsonDeserializer());
    }

    @SuppressWarnings("rawtypes")
    private static class UnitJsonDeserializer extends StdScalarDeserializer<Unit> {
        /**
         *
         */
        @Serial
        private static final long serialVersionUID = -6327531740958676293L;

        protected UnitJsonDeserializer() {
            super(Unit.class);
        }

        @Override
        public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            JsonToken currentToken = jsonParser.getCurrentToken();

            if (currentToken == JsonToken.VALUE_STRING) {
                return parse(jsonParser, jsonParser.getText());
            }
            throw deserializationContext.wrongTokenException(
                    jsonParser, String.class, JsonToken.VALUE_STRING, "Expected unit value in String format");
        }

        private static Unit<?> parse(JsonParser jp, String unitString) throws JsonParseException {
            return switch (unitString.toLowerCase()) {
                case "g", "gram", "grams" -> Units.GRAM;
                case "kg", "kilogram", "kilograms" -> Units.KILOGRAM;
                case "ml", "millilitre", "millilitres" -> Units.LITRE.divide(1000);
                case "l", "litre", "litres" -> Units.LITRE;
                case "tbsp", "tablespoon", "tablespoons" -> Units.LITRE.multiply(
                        ((double) 15 / 1000)); // Example: 1 tbsp = 15 ml
                default -> throw new JsonParseException(jp, "error deserializing unit.");
            };
        }
    }
}
