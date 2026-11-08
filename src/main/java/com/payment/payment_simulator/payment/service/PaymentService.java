package com.payment.payment_simulator.payment.service;

import com.payment.payment_simulator.payment.dto.PaymentRequest;
import com.payment.payment_simulator.payment.dto.PaymentResponse;
import com.payment.payment_simulator.payment.dto.PaymentStatusResponse;
import com.payment.payment_simulator.payment.dto.PaymentWebhookRequest;
import com.payment.payment_simulator.payment.entity.Payment;
import com.payment.payment_simulator.payment.enums.PaymentStatus;
import com.payment.payment_simulator.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;
//    private final WebhookService webhookService;

    public PaymentResponse createPayment(
            PaymentRequest request) {

        Payment payment = Payment.builder()
                .paymentReference(request.paymentReference())
                .amount(request.amount())
                .status(PaymentStatus.PENDING)
                .build();

        payment = paymentRepository.save(payment);

        paymentProcessor.processPayment(payment.getId());

        return new PaymentResponse(
                payment.getId(),
                payment.getPaymentReference(),
                payment.getAmount(),
                payment.getStatus()
        );
    }

    public PaymentStatusResponse getPaymentStatus(String paymentReference) {

        Payment payment = paymentRepository
                .findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Payment not found: " + paymentReference
                        )
                );

        return new PaymentStatusResponse(
                payment.getPaymentReference(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getTransactionRef()
        );
    }
}