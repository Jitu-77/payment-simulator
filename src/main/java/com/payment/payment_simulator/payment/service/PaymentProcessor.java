package com.payment.payment_simulator.payment.service;

import com.payment.payment_simulator.payment.entity.Payment;
import com.payment.payment_simulator.payment.entity.WebhookEvent;
import com.payment.payment_simulator.payment.enums.PaymentStatus;
import com.payment.payment_simulator.payment.enums.WebhookEventStatus;
import com.payment.payment_simulator.payment.repository.PaymentRepository;
import com.payment.payment_simulator.payment.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentProcessor {

    private final PaymentRepository paymentRepository;
    private final WebhookEventRepository webhookEventRepository;
    private final WebhookService webhookService;

    @Async
    public void processPayment(Long paymentId) {

        try {

            Thread.sleep(10_000);

            Payment payment = paymentRepository
                    .findById(paymentId)
                    .orElseThrow();

            boolean success =
                    ThreadLocalRandom.current()
                            .nextInt(100) < 90;

            if (success) {

                payment.setStatus(PaymentStatus.SUCCESS);

                payment.setTransactionRef(
                        "TXN-" + UUID.randomUUID()
                );

            } else {

                payment.setStatus(PaymentStatus.FAILED);
            }

            paymentRepository.save(payment);

            // Create webhook AFTER payment result is known
            WebhookEvent event = WebhookEvent.builder()
                    .eventId("WH-" + UUID.randomUUID())
                    .paymentReference(payment.getPaymentReference())
                    .transactionRef(payment.getTransactionRef())
                    .paymentStatus(payment.getStatus())
                    .status(WebhookEventStatus.PENDING)
                    .attempts(0)
                    .build();

            event = webhookEventRepository.save(event);

            // Send the saved event
            webhookService.sendWebhook(event);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}