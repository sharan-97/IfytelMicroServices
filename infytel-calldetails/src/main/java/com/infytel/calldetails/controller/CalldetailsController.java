package com.infytel.calldetails.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infytel.calldetails.dto.CallDetailsDTO;
import com.infytel.calldetails.service.CallDetailsService;

@RestController
@CrossOrigin
public class CalldetailsController {
	
	@Autowired
	CallDetailsService callDetailsService;
	  
	@GetMapping(value="/customers/{phoneNo}/calldetails")
	List<CallDetailsDTO>getCustomerCallDetails(@PathVariable Long phoneNo){

        return callDetailsService.getCustomerCallDetails(phoneNo);

	}

}
