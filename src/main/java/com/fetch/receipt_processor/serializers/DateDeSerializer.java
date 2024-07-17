package com.fetch.receipt_processor.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class DateDeSerializer extends StdDeserializer<Date> {
    public DateDeSerializer() {
        super(Date.class);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String s = jsonParser.readValueAs(String.class);
        try {
            return DateUtils.parseDate(s, "yyyy-MM-dd");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
