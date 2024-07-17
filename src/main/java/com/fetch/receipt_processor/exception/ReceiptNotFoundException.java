package com.fetch.receipt_processor.exception;

import java.util.UUID;

public class ReceiptNotFoundException extends Exception {

    private UUID uuid;

    public ReceiptNotFoundException(UUID uuid) {
        super(String.format("No receipt found for id:= %s", uuid.toString()));
        this.uuid = uuid;
    }
}
