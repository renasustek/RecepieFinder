package com.github.renas.recipe.jackson;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.renas.recipe.measurment.Mass;
import com.github.renas.recipe.measurment.NoUnit;
import com.github.renas.recipe.measurment.Quantity;
import com.github.renas.recipe.measurment.Unit;
import com.github.renas.recipe.measurment.Volume;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantitySerializerTest {

    private JsonGenerator jsonGenerator;
    private SerializerProvider serializerProvider;
    private QuantitySerializer serializer;

    @BeforeEach
    void setUp() {
        jsonGenerator = mock(JsonGenerator.class);
        serializerProvider = mock(SerializerProvider.class);
        serializer = new QuantitySerializer();
    }

    @Test
    void serializeMassSuccessfully() throws IOException {
        Quantity mass = new Mass(5, Unit.KILOGRAM);
        serializer.serialize(mass, jsonGenerator, serializerProvider);
        verify(jsonGenerator).writeStartObject();
        verify(jsonGenerator).writeNumberField("quantity", mass.value());
        verify(jsonGenerator).writeStringField("unit", mass.unit().name());
        verify(jsonGenerator).writeEndObject();
    }

    @Test
    void serializeVolumeSuccessfully() throws IOException {
        Quantity volume = new Volume(1, Unit.LITER);
        serializer.serialize(volume, jsonGenerator, serializerProvider);
        verify(jsonGenerator).writeStartObject();
        verify(jsonGenerator).writeNumberField("quantity", volume.value());
        verify(jsonGenerator).writeStringField("unit", volume.unit().name());
        verify(jsonGenerator).writeEndObject();
    }

    @Test
    void serializeNoUnitSuccessfully() throws IOException {
        Quantity noUnit = new NoUnit(10);
        serializer.serialize(noUnit, jsonGenerator, serializerProvider);
        verify(jsonGenerator).writeStartObject();
        verify(jsonGenerator).writeNumberField("quantity", noUnit.value());
        verify(jsonGenerator).writeStringField("unit", noUnit.unit().name());
        verify(jsonGenerator).writeEndObject();
    }
}
