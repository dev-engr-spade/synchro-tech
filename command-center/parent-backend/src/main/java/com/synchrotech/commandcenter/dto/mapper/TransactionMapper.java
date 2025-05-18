package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Transaction;
import com.synchrotech.commandcenter.model.module.TransactionType;
import com.synchrotech.commandcenter.model.module.TransactionStatus;
import com.synchrotech.commandcenter.dto.request.module.TransactionRequest;

public class TransactionMapper {
    public static Transaction toEntity(TransactionRequest request) {
        if (request == null) return null;
        return Transaction.builder()
                .tenantId(request.getTenantId())
                .referenceNumber(request.getReferenceNumber())
                .type(request.getType() != null ? TransactionType.valueOf(request.getType()) : null)
                .sourceId(request.getSourceId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .status(request.getStatus() != null ? TransactionStatus.valueOf(request.getStatus()) : null)
                .userId(request.getUserId())
                .notes(request.getNotes())
                .transactionDate(request.getTransactionDate())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 