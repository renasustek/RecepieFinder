package com.github.renas.recipe.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import javax.measure.Quantity;
import javax.measure.Unit;
import tech.units.indriya.quantity.Quantities;

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
        BigDecimal value = codec.treeToValue(root.get("value"), BigDecimal.class);
        Unit<?> unit = codec.treeToValue(root.get("unit"), Unit.class);

        return Quantities.getQuantity(value, unit);
    }
}
