package com.infytel.calldetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InfytelCalldetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfytelCalldetailsApplication.class, args);
	}

}
