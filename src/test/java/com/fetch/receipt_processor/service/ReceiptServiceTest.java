package com.fetch.receipt_processor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.receipt_processor.dto.ReceiptRequestDto;
import com.fetch.receipt_processor.entity.ReceiptDbo;
import com.fetch.receipt_processor.exception.ReceiptNotFoundException;
import com.fetch.receipt_processor.mappers.ReceiptMapper;
import com.fetch.receipt_processor.mappers.ReceiptMapperImpl;
import com.fetch.receipt_processor.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ReceiptMapperImpl.class})
class ReceiptServiceTest {

    @Mock
    ReceiptMapper receiptMapper;

    @Mock
    ReceiptRepository receiptRepository;

    @InjectMocks
    ReceiptService receiptService;

    @Autowired
    ReceiptMapper receiptMapperImpl;

    String payload1 = """
            {
              "retailer": "Target",
              "purchaseDate": "2022-01-01",
              "purchaseTime": "13:01",
              "items": [
                {
                  "shortDescription": "Mountain Dew 12PK",
                  "price": "6.49"
                },{
                  "shortDescription": "Emils Cheese Pizza",
                  "price": "12.25"
                },{
                  "shortDescription": "Knorr Creamy Chicken",
                  "price": "1.26"
                },{
                  "shortDescription": "Doritos Nacho Cheese",
                  "price": "3.35"
                },{
                  "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
                  "price": "12.00"
                }
              ],
              "total": "35.35"
            }
            """;

    String payload2 = """
            {
              "retailer": "M&M Corner Market",
              "purchaseDate": "2022-03-20",
              "purchaseTime": "14:33",
              "items": [
                {
                  "shortDescription": "Gatorade",
                  "price": "2.25"
                },{
                  "shortDescription": "Gatorade",
                  "price": "2.25"
                },{
                  "shortDescription": "Gatorade",
                  "price": "2.25"
                },{
                  "shortDescription": "Gatorade",
                  "price": "2.25"
                }
              ],
              "total": "9.00"
            }
            """;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addReceipt() {
        ReceiptRequestDto receiptRequest = new ReceiptRequestDto();
        ReceiptDbo receiptDbo = new ReceiptDbo();
        receiptDbo.setItems(Collections.emptyList());
        UUID uuid = UUID.randomUUID();
        receiptDbo.setId(uuid);
        Mockito.when(receiptMapper.map(receiptRequest)).thenReturn(receiptDbo);
        Mockito.when(receiptRepository.save(receiptDbo)).thenReturn(receiptDbo);
        assertEquals(uuid, receiptService.addReceipt(receiptRequest));
        Mockito.verify(receiptRepository).save(receiptDbo);
    }

    private ReceiptDbo getReceipt(String payload1) throws JsonProcessingException {
        ReceiptRequestDto receiptRequestDto = new ObjectMapper().readValue(payload1, ReceiptRequestDto.class);
        ReceiptDbo receiptDbo = receiptMapperImpl.map(receiptRequestDto);
        return receiptDbo;
    }

    @Test
    void calculatePoints() throws JsonProcessingException, ReceiptNotFoundException {
        UUID uuid = UUID.randomUUID();
        Mockito.when(receiptRepository.findById(uuid)).thenReturn(Optional.of(getReceipt(payload1)));
        assertEquals(28, receiptService.calculatePoints(uuid));
    }

    @Test
    void calculatePoints_1() throws JsonProcessingException, ReceiptNotFoundException {
        UUID uuid = UUID.randomUUID();
        Mockito.when(receiptRepository.findById(uuid)).thenReturn(Optional.of(getReceipt(payload2)));
        assertEquals(109, receiptService.calculatePoints(uuid));
    }
}