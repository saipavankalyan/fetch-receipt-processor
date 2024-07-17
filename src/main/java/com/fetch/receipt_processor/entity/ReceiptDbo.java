package com.fetch.receipt_processor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RECEIPT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String retailer;

    @Column(name = "PURCHASE_DATE")
    private Date purchaseDate;

    @Column(name = "PURCHASE_TIME")
    private LocalTime purchaseTime;
    private BigDecimal total;

    @OneToMany(targetEntity = ItemDbo.class, cascade = CascadeType.ALL, mappedBy = "receiptId")
    private List<ItemDbo> items;

}
