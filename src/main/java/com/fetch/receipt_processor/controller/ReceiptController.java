package com.fetch.receipt_processor.controller;

import com.fetch.receipt_processor.dto.ReceiptPointsDto;
import com.fetch.receipt_processor.dto.ReceiptProcessResponseDto;
import com.fetch.receipt_processor.dto.ReceiptRequestDto;
import com.fetch.receipt_processor.exception.ReceiptNotFoundException;
import com.fetch.receipt_processor.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@AllArgsConstructor
public class ReceiptController {

    private ReceiptService receiptService;

    @PostMapping(value = "/process")
    @ResponseStatus(HttpStatus.OK)
    public ReceiptProcessResponseDto processReceipt(@RequestBody ReceiptRequestDto request) {
        UUID uuid = receiptService.addReceipt(request);
        return ReceiptProcessResponseDto.builder().id(uuid.toString()).build();
    }

    @GetMapping("/{receiptId}/points")
    public ReceiptPointsDto getPoints(@PathVariable("receiptId") UUID receiptId) throws ReceiptNotFoundException {
        Integer points = receiptService.calculatePoints(receiptId);
        return ReceiptPointsDto.builder().points(points).build();
    }

}
