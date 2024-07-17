package com.fetch.receipt_processor.repository;

import com.fetch.receipt_processor.entity.ReceiptDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptDbo, UUID> {

}
