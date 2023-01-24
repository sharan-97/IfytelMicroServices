package com.infytel.plans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InfytelPlansApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfytelPlansApplication.class, args);
	}

}
