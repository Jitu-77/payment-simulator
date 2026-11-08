package com.payment.payment_simulator.payment.dto;

import com.payment.payment_simulator.payment.enums.PaymentStatus;

public record  PaymentWebhookRequest(
        String eventId,
        String paymentReference,
        String transactionRef,
        PaymentStatus status
) {
}
