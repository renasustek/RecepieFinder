package com.github.renas.recipe.jackson;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.renas.recipe.measurment.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantityDeserializerTest {

    private JsonParser jsonParser;
    private DeserializationContext deserializationContext;
    private ObjectCodec codec;
    private QuantityDeserializer deserializer;

    @BeforeEach
    void setUp() {
        jsonParser = mock(JsonParser.class);
        deserializationContext = mock(DeserializationContext.class);
        codec = mock(ObjectCodec.class);
        when(jsonParser.getCodec()).thenReturn(codec);

        deserializer = new QuantityDeserializer();
    }

    @Test
    void whenValueNullShouldThrowException() throws IOException {
        ObjectNode rootNode = mock(ObjectNode.class);
        when(jsonParser.readValueAsTree()).thenReturn(rootNode);
        when(rootNode.get("value")).thenReturn(null);
        when(rootNode.get("unit")).thenReturn(mock(JsonNode.class));

        JsonParseException exception = assertThrows(
                JsonParseException.class, () -> deserializer.deserialize(jsonParser, deserializationContext));

        assertEquals("Value not found for quantity type.", exception.getMessage());
    }

    @Test
    void whenUnitNullShouldThrowException() throws IOException {
        ObjectNode rootNode = mock(ObjectNode.class);
        when(jsonParser.readValueAsTree()).thenReturn(rootNode);
        when(rootNode.get("value")).thenReturn(mock(JsonNode.class));
        when(rootNode.get("unit")).thenReturn(null);

        JsonParseException exception = assertThrows(
                JsonParseException.class, () -> deserializer.deserialize(jsonParser, deserializationContext));

        assertEquals("Unit not found for quantity type.", exception.getMessage());
    }

    @Test
    void deserializeMassSuccessfully() throws Exception {
        // Setup mock data for a mass unit (e.g., kilogram)
        ObjectNode rootNode = mock(ObjectNode.class);
        JsonNode valueNode = mock(JsonNode.class);
        JsonNode unitNode = mock(JsonNode.class);

        when(jsonParser.readValueAsTree()).thenReturn(rootNode);
        when(rootNode.get("value")).thenReturn(valueNode);
        when(rootNode.get("unit")).thenReturn(unitNode);
        when(codec.treeToValue(valueNode, Integer.class)).thenReturn(5); // 5 kg
        when(codec.treeToValue(unitNode, Unit.class)).thenReturn(Unit.KILOGRAM);

        Quantity result = deserializer.deserialize(jsonParser, deserializationContext);

        assertTrue(result instanceof Mass);
        assertEquals(5, ((Mass) result).value());
        assertEquals(Unit.KILOGRAM, ((Mass) result).unit());
    }

    @Test
    void deserializeVolumeSuccessfully() throws Exception {
        // Setup mock data for a volume unit (e.g., liter)
        ObjectNode rootNode = mock(ObjectNode.class);
        JsonNode valueNode = mock(JsonNode.class);
        JsonNode unitNode = mock(JsonNode.class);

        when(jsonParser.readValueAsTree()).thenReturn(rootNode);
        when(rootNode.get("value")).thenReturn(valueNode);
        when(rootNode.get("unit")).thenReturn(unitNode);
        when(codec.treeToValue(valueNode, Integer.class)).thenReturn(1); // 1 liter
        when(codec.treeToValue(unitNode, Unit.class)).thenReturn(Unit.LITER);

        Quantity result = deserializer.deserialize(jsonParser, deserializationContext);

        assertTrue(result instanceof Volume);
        assertEquals(1, ((Volume) result).value());
        assertEquals(Unit.LITER, ((Volume) result).unit());
    }
}
