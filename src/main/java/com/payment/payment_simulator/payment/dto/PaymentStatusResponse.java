package com.payment.payment_simulator.payment.dto;

import com.payment.payment_simulator.payment.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentStatusResponse(
        String paymentReference,
        BigDecimal amount,
        PaymentStatus status,
        String transactionRef
) {
}
