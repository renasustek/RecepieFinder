package com.github.renas.recipe.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.renas.recipe.measurment.*;
import java.io.IOException;

@SuppressWarnings("rawtypes")
public class QuantityDeserializer extends StdDeserializer<Quantity> {

    public QuantityDeserializer() {
        super(Quantity.class);
    }

    @Override
    public Quantity deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        TreeNode root = jp.readValueAsTree();
        if (root.get("value") == null) {
            throw new JsonParseException(jp, "Value not found for quantity type.");
        }
        if (root.get("unit") == null) {
            throw new JsonParseException(jp, "Unit not found for quantity type.");
        }

        ObjectCodec codec = jp.getCodec();
        Integer value = codec.treeToValue(root.get("value"), Integer.class);
        Unit unit = codec.treeToValue(root.get("unit"), Unit.class);
        return switch (unit) {
            case KILOGRAM, GRAM -> new Mass(value, unit);
            case MILLILITER, LITER, TABLESPOON -> new Volume(value, unit);
            case null -> null;
            default -> new NoUnit(value);
        };
    }
}
