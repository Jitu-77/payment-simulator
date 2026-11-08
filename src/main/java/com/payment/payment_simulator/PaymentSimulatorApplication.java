package com.payment.payment_simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableAsync
public class PaymentSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSimulatorApplication.class, args);
	}
//	@Bean
//	public RestClient.Builder restClientBuilder() {
//		return RestClient.builder();
//	}
}
