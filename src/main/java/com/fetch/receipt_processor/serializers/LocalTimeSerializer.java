package com.fetch.receipt_processor.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalTime;

import static com.fetch.receipt_processor.constants.ApplicationConstants.TIME_FORMAT;

public class LocalTimeSerializer extends StdSerializer<LocalTime> {

    public LocalTimeSerializer() {
        super(LocalTime.class);
    }

    @Override
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localTime.format(TIME_FORMAT));
    }
}
