package com.fetch.receipt_processor.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalTime;

import static com.fetch.receipt_processor.constants.ApplicationConstants.TIME_FORMAT;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

    public LocalTimeDeserializer() {
        super(LocalTime.class);
    }

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String timeString = jsonParser.readValueAs(String.class);
        return LocalTime.parse(timeString, TIME_FORMAT);
    }
}
