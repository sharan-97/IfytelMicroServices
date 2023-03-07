package com.infytel.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class InfytelCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfytelCustomerApplication.class, args);
	}

}
