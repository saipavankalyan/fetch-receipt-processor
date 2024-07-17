package com.fetch.receipt_processor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ITEM")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemDbo {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @ManyToOne(targetEntity = ReceiptDbo.class)
    @JoinColumn(name = "RECEIPT_ID")
    private ReceiptDbo receipt;

    @Column(name = "SHORT_DESCRIPTION")
    private String shortDescription;
    private BigDecimal price;

}
