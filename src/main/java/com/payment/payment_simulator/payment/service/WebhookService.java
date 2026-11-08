package com.payment.payment_simulator.payment.service;

import com.payment.payment_simulator.payment.dto.PaymentWebhookRequest;
import com.payment.payment_simulator.payment.entity.WebhookEvent;
import com.payment.payment_simulator.payment.enums.WebhookEventStatus;
import com.payment.payment_simulator.payment.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookEventRepository webhookEventRepository;

    private final RestClient.Builder restClientBuilder;

    @Value("${webhook.ecomm-url}")
    private String ecommWebhookUrl;

    public void sendWebhook(WebhookEvent event) {

        event.setAttempts(event.getAttempts() + 1);

        webhookEventRepository.save(event);

        PaymentWebhookRequest request =
                new PaymentWebhookRequest(
                        event.getEventId(),
                        event.getPaymentReference(),
                        event.getTransactionRef(),
                        event.getPaymentStatus()
                );

        try {

            ResponseEntity<String> response =
                    restClientBuilder
                            .build()
                            .post()
                            .uri(ecommWebhookUrl)
                            .body(request)
                            .retrieve()
                            .toEntity(String.class);

            if (response.getStatusCode().is2xxSuccessful()) {

//                event.setStatus(WebhookEventStatus.DELIVERED);
//                event.setDeliveredAt(LocalDateTime.now());
//
//                webhookEventRepository.save(event);

                System.out.println(
                        "Webhook accepted by Ecomm. Waiting for acknowledgement: "
                                + event.getEventId()
                );
            }

        } catch (Exception e) {

            event.setStatus(WebhookEventStatus.FAILED);

            webhookEventRepository.save(event);

            System.out.println(
                    "Webhook delivery failed: " + e.getMessage()
            );
        }
    }
}