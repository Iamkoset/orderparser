package com.koset.orderparser.model.serialize;

import com.koset.orderparser.util.ValueParser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StringToNumberSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(
            String value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {

        jsonGenerator.writeObject(ValueParser.parseNumber(value));
    }

}