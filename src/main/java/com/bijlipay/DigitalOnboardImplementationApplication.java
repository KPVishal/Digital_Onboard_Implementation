package com.bijlipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "com.bijlipay.*")
public class DigitalOnboardImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalOnboardImplementationApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public AsyncRestTemplate asyncRestTemplate(){
		return new AsyncRestTemplate();
	}
}
