package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity representing a transaction.
 */
@Document(collection = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private String id;
    private String tenantId;
    private String referenceNumber;
    private TransactionType type;
    private String sourceId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private TransactionStatus status;
    private String userId;
    private String notes;
    private Date transactionDate;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 