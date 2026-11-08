package com.payment.payment_simulator.payment.service;

import com.payment.payment_simulator.payment.entity.WebhookEvent;
import com.payment.payment_simulator.payment.enums.WebhookEventStatus;
import com.payment.payment_simulator.payment.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebhookAcknowledgementService {

    private final WebhookEventRepository webhookEventRepository;

    public void acknowledgeWebhook(String eventId) {

        WebhookEvent event = webhookEventRepository
                .findByEventId(eventId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Webhook event not found: " + eventId
                        )
                );

        if (event.getStatus() == WebhookEventStatus.DELIVERED) {
            return;
        }

        event.setStatus(WebhookEventStatus.DELIVERED);
        event.setDeliveredAt(LocalDateTime.now());

        webhookEventRepository.save(event);
    }
}