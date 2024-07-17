package com.fetch.receipt_processor.service;

import com.fetch.receipt_processor.dto.ReceiptRequestDto;
import com.fetch.receipt_processor.entity.ItemDbo;
import com.fetch.receipt_processor.entity.ReceiptDbo;
import com.fetch.receipt_processor.exception.ReceiptNotFoundException;
import com.fetch.receipt_processor.mappers.ReceiptMapper;
import com.fetch.receipt_processor.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.CharUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.UUID;

import static com.fetch.receipt_processor.constants.ApplicationConstants.TIME_FORMAT;

@Service
@AllArgsConstructor
public class ReceiptService {

    private static final LocalTime START_TIME = LocalTime.parse("14:00", TIME_FORMAT);
    private static final LocalTime END_TIME = LocalTime.parse("16:00", TIME_FORMAT);
    private ReceiptMapper receiptMapper;
    private ReceiptRepository receiptRepository;

    public UUID addReceipt(ReceiptRequestDto receiptRequest) {
        final ReceiptDbo receiptDbo = receiptMapper.map(receiptRequest);
        receiptDbo.getItems().forEach(itemDbo -> itemDbo.setReceipt(receiptDbo));
        ReceiptDbo savedReceiptDbo = receiptRepository.save(receiptDbo);
        return savedReceiptDbo.getId();
    }

    public Integer calculatePoints(UUID receiptId) throws ReceiptNotFoundException {
        ReceiptDbo receiptDbo = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ReceiptNotFoundException(receiptId));

        Integer points = 0;
        // Rule - One point for every alphanumeric character in the retailer name.
        for (Character ch : receiptDbo.getRetailer().toCharArray()) {
            if (CharUtils.isAsciiAlphanumeric(ch)) {
                points += 1;
            }
        }

        // Rule - 50 points if the total is a round dollar amount with no cents.
        if (receiptDbo.getTotal().remainder(BigDecimal.ONE).setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
            points += 50;
        }

        // Rule - 25 points if the total is a multiple of 0.25.
        if (receiptDbo.getTotal().multiply(BigDecimal.valueOf(100)).remainder(BigDecimal.valueOf(25)).setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
            points += 25;
        }

        // Rule - 5 points for every two items on the receipt.
        points += (receiptDbo.getItems().size() / 2) * 5;

        // Rule - If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        for (ItemDbo item : receiptDbo.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += item.getPrice().multiply(BigDecimal.valueOf(0.2)).setScale(0, RoundingMode.CEILING).intValue();
            }
        }

        // Rule - 6 points if the day in the purchase date is odd.
        if (receiptDbo.getPurchaseDate().getDate() % 2 == 1) {
            points += 6;
        }

        // Rule - 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        if (receiptDbo.getPurchaseTime().isAfter(START_TIME) && receiptDbo.getPurchaseTime().isBefore(END_TIME)) {
            points += 10;
        }

        return points;
    }

}
