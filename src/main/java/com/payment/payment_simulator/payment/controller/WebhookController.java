package com.payment.payment_simulator.payment.controller;

import com.payment.payment_simulator.payment.service.WebhookAcknowledgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookAcknowledgementService webhookAcknowledgementService;

    @PostMapping("/{eventId}/ack")
    public ResponseEntity<Void> acknowledgeWebhook(
            @PathVariable String eventId
    ) {

        webhookAcknowledgementService.acknowledgeWebhook(eventId);

        return ResponseEntity.noContent().build();
    }
}