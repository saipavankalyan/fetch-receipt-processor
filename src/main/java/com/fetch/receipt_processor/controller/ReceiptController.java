package com.fetch.receipt_processor.controller;

import com.fetch.receipt_processor.dto.ReceiptPointsDto;
import com.fetch.receipt_processor.dto.ReceiptProcessResponseDto;
import com.fetch.receipt_processor.dto.ReceiptRequestDto;
import com.fetch.receipt_processor.exception.ReceiptNotFoundException;
import com.fetch.receipt_processor.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@AllArgsConstructor
public class ReceiptController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptController.class);
    private ReceiptService receiptService;

    @PostMapping(value = "/process")
    @ResponseStatus(HttpStatus.OK)
    public ReceiptProcessResponseDto processReceipt(@RequestBody ReceiptRequestDto request) {
        UUID uuid = receiptService.addReceipt(request);
        LOGGER.info("Receipt [id = {}] added to the system", uuid.toString());
        return ReceiptProcessResponseDto.builder().id(uuid.toString()).build();
    }

    @GetMapping("/{receiptId}/points")
    public ReceiptPointsDto getPoints(@PathVariable("receiptId") UUID receiptId) throws ReceiptNotFoundException {
        Integer points = receiptService.calculatePoints(receiptId);
        LOGGER.info("Calculation of points for Receipt [id = {}] resulted in [ {} ] points", receiptId.toString(), points);
        return ReceiptPointsDto.builder().points(points).build();
    }

}
