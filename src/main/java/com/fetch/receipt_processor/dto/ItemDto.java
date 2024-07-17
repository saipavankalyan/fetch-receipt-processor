package com.fetch.receipt_processor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto implements Serializable {

    private String shortDescription;
    // We are using BigDecimal to avoid floating point precision errors. It's MONEY, so, be safe.
    private BigDecimal price;

}
