package com.fetch.receipt_processor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fetch.receipt_processor.serializers.DateDeSerializer;
import com.fetch.receipt_processor.serializers.LocalTimeDeserializer;
import com.fetch.receipt_processor.serializers.LocalTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptRequestDto implements Serializable {

    private String retailer;

    @JsonDeserialize(using = DateDeSerializer.class)
    private Date purchaseDate;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime purchaseTime;

    private List<ItemDto> items;
    private BigDecimal total;

}
