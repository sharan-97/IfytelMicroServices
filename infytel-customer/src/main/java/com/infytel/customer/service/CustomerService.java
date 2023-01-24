package com.infytel.customer.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infytel.customer.dto.CustomerDTO;
import com.infytel.customer.dto.LoginDTO;
import com.infytel.customer.entity.Customer;
import com.infytel.customer.repository.CustomerRepository;
@Service
public class CustomerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerRepository custRepo;


	// Create a new customer
	public void createCustomer(CustomerDTO custDTO) {
		logger.info("Creation request for customer {}", custDTO);
		Customer cust = custDTO.createEntity();
		custRepo.save(cust);
	}

	// Login
	public boolean login(LoginDTO loginDTO) {
		Boolean flag=false;
		logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
		Optional<Customer> cust;
		cust=custRepo.findById(loginDTO.getPhoneNo());
		if(cust.isPresent() && cust.get() != null && cust.get().getPassword().equals(loginDTO.getPassword())) {
			flag= true;
		}
		return flag;
	}

	// Fetches full profile of a specific customer
	public CustomerDTO getCustomerProfile(Long phoneNo) {
		CustomerDTO custDTO=null;
		logger.info("Profile request for customer {}", phoneNo);
		Optional<Customer> cust = custRepo.findById(phoneNo);
		if(cust.isPresent())
			custDTO = CustomerDTO.valueOf(cust.get());
		logger.info("Profile for customer : {}", custDTO);
		return custDTO;
	}





}
