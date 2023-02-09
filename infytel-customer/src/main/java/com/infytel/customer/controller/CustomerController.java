package com.infytel.customer.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infytel.customer.dto.CustomerDTO;
import com.infytel.customer.dto.LoginDTO;
import com.infytel.customer.dto.PlanDTO;
import com.infytel.customer.service.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


//@RefreshScope
//refresh scope is added to pick up the application.properties from git every time we do /refresh
@RestController
@CrossOrigin
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerService custService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient;

	// Create a new customer
	@PostMapping(value = "/customers",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createCustomer(@RequestBody CustomerDTO custDTO) {
		//my changes
		ObjectMapper om = new ObjectMapper();
		try {
			System.out.println(om.writeValueAsString(custDTO));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Creation request for customer {}", custDTO);
		custService.createCustomer(custDTO);
	}

	// Login
	
	@PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@RequestBody LoginDTO loginDTO) {
		logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
		return custService.login(loginDTO);
	}

	// Fetches full profile of a specific customer
	
	@HystrixCommand(fallbackMethod = "getCustomerProfileFallBack1")
	@GetMapping(value = "/customers/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfile(@PathVariable Long phoneNo) throws InterruptedException {

		logger.info("Profile request for customer {}", phoneNo);
		System.out.println("*************in temp menthod*****************");
		
		CustomerDTO customerDTO =custService.getCustomerProfile(phoneNo);
		
//		List<ServiceInstance> planInstance =   discoveryClient.getInstances("INFYTELPLANS");
//		URI  planUri = planInstance.get(0).getUri();
//		System.out.println(planUri);
		
		PlanDTO planDTO = restTemplate.getForObject("http://INFYTELPLANS/plans/"+customerDTO.getCurrentPlan().getPlanId(), PlanDTO.class);
		System.out.println("inside cust controller");
		System.out.println(phoneNo);
		
//		List<ServiceInstance> friendInstance =   discoveryClient.getInstances("INFYTELFRIENDFAMILY");
//		URI  friendUri = friendInstance.get(2).getUri();
//		System.out.println(friendUri);
		
		@SuppressWarnings("unchecked")
		List<Long> friends = restTemplate.getForObject("http://INFYTELFRIENDFAMILY/customers/getlist/"+phoneNo, List.class);
		for(Long num : friends ) {
			System.out.println(num);
		}
		customerDTO.setFriendAndFamily(friends);
		customerDTO.setCurrentPlan(planDTO);
		return customerDTO;
	}
	
	public CustomerDTO getCustomerProfileFallBack1(Long phoneNo) {
		System.out.println("###########Inside fall back menthod#######");
		return new CustomerDTO();
	}
	



}
