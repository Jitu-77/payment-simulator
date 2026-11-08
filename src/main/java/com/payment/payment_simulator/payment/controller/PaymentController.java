package com.payment.payment_simulator.payment.controller;

import com.payment.payment_simulator.payment.dto.PaymentRequest;
import com.payment.payment_simulator.payment.dto.PaymentResponse;
import com.payment.payment_simulator.payment.dto.PaymentStatusResponse;
import com.payment.payment_simulator.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.createPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/{paymentReference}")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(
            @PathVariable String paymentReference) {

        return ResponseEntity.ok(
                paymentService.getPaymentStatus(paymentReference)
        );
    }

}