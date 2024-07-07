package com.github.renas.recipe.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;
import javax.measure.Quantity;

@SuppressWarnings("rawtypes")
public class QuantitySerializer extends StdScalarSerializer<Quantity> {

    protected QuantitySerializer() {
        super(Quantity.class);
    }

    @Override
    public void serialize(Quantity quantity, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("quantity", quantity.getValue().toString());
        gen.writeStringField("unit", quantity.getUnit().toString());
        gen.writeEndObject();
    }
}
