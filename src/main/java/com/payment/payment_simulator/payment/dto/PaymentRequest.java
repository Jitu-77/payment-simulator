package com.payment.payment_simulator.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank
        String paymentReference,

        @NotNull
        @Positive
        BigDecimal amount
) {
}
