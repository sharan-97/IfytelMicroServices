package com.infytel.customer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.infytel.customer.service.RestAPICalls;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.concurrent.Future;


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
	
	@Autowired
	RestAPICalls restAPICalls;

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
	
	@CircuitBreaker(name = "customerService", fallbackMethod = "getCustomerProfileFallBack")
	@GetMapping(value = "/customers/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfile(@PathVariable Long phoneNo) {
		long overAllStart = System.currentTimeMillis(); 
		
		logger.info("Profile request for customer {}", phoneNo);
		System.out.println("*************in temp menthod*****************");
		
		CustomerDTO customerDTO =custService.getCustomerProfile(phoneNo);
		
//		List<ServiceInstance> planInstance =   discoveryClient.getInstances("INFYTELPLANS");
//		URI  planUri = planInstance.get(0).getUri();
//		System.out.println(planUri);

		//below code has been replaced with a separate class to test resilience 
//		PlanDTO planDTO = restTemplate.getForObject("http://INFYTELPLANS/plans/"+customerDTO.getCurrentPlan().getPlanId(), PlanDTO.class);
//		System.out.println("inside cust controller");
//		System.out.println(phoneNo);
		long planStart = System.currentTimeMillis(); 
		logger.info("gonna call plan service");
		Future<PlanDTO> planDTO = restAPICalls.callPlanService(customerDTO.getCurrentPlan().getPlanId());
		logger.info("done calling plan service");
		long planStop = System.currentTimeMillis();
		
		
//		List<ServiceInstance> friendInstance =   discoveryClient.getInstances("INFYTELFRIENDFAMILY");
//		URI  friendUri = friendInstance.get(2).getUri();
//		System.out.println(friendUri);
		
		
        //below code has been replaced with a separate class to test resilince 
//		@SuppressWarnings("unchecked")
//		List<Long> friends = restTemplate.getForObject("http://INFYTELFRIENDFAMILY/customers/getlist/"+phoneNo, List.class);
		long friendStart = System.currentTimeMillis(); 
		logger.info("gonna call friend service");
		Future<List<Long>> friends = restAPICalls.callFriendsService(phoneNo);
		logger.info("done calling friend service");
		long friendStop = System.currentTimeMillis(); 
		
//		for(Long num : friends ) {
//			System.out.println(num);
//		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("calling get method on friend and family");
		customerDTO.setFriendAndFamily(friends.get());
		logger.info("calling get method on plan");
		customerDTO.setCurrentPlan(planDTO.get());
		logger.info("done calling get method on plan");
		long overAllStop = System.currentTimeMillis(); 
		logger.info("Total time for Plan "+(planStop-planStart)); 
		logger.info("Total time for Friend "+(friendStop-friendStart)); 
		logger.info("Total Overall time for Request "+(overAllStop-overAllStart)); 
		return customerDTO;
	}
	
	public CustomerDTO getCustomerProfileFallBack(Long phoneNo, Throwable throwable) {
		System.out.println("###########Inside fall back menthod#######");
		return new CustomerDTO();
	}
	



}
