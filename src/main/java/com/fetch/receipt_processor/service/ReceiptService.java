package com.fetch.receipt_processor.service;

import com.fetch.receipt_processor.dto.ReceiptRequestDto;
import com.fetch.receipt_processor.entity.ReceiptDbo;
import com.fetch.receipt_processor.mappers.ReceiptMapper;
import com.fetch.receipt_processor.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReceiptService {

    private ReceiptMapper receiptMapper;
    private ReceiptRepository receiptRepository;

    public UUID addReceipt(ReceiptRequestDto receiptRequest) {
        ReceiptDbo receiptDbo = receiptMapper.map(receiptRequest);
        receiptDbo = receiptRepository.save(receiptDbo);
        return receiptDbo.getId();
    }

}
