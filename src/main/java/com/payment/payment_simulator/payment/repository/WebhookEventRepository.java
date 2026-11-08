package com.payment.payment_simulator.payment.repository;

import com.payment.payment_simulator.payment.entity.WebhookEvent;
import com.payment.payment_simulator.payment.enums.WebhookEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebhookEventRepository
        extends JpaRepository<WebhookEvent, Long> {

    Optional<WebhookEvent> findByEventId(String eventId);

    List<WebhookEvent> findByStatus(WebhookEventStatus status);
}
