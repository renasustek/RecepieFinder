package com.github.renas.recipe.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.github.renas.recipe.measurment.Quantity;
import java.io.IOException;

@SuppressWarnings("rawtypes")
public class QuantitySerializer extends StdScalarSerializer<Quantity> {

    protected QuantitySerializer() {
        super(Quantity.class);
    }

    @Override
    public void serialize(Quantity quantity, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("quantity", quantity.value());
        gen.writeStringField("unit", quantity.unit().toString());
        gen.writeEndObject();
    }
}
