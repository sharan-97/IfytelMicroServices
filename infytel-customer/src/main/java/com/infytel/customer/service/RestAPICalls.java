package com.infytel.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infytel.customer.dto.PlanDTO;

import io.vavr.concurrent.Future;


@Service
public class RestAPICalls {

	@Autowired
	RestTemplate restTemplate;


	public Future<PlanDTO> callPlanService(Integer planId) {
		
		
		return Future.of(()->restTemplate.getForObject("http://localhost:8003/plans/"+planId, PlanDTO.class));

	}

	public Future<List<Long>> callFriendsService(Long phoneNo) {
		System.out.println("in rest apiclass");
		
		return Future.of(()->restTemplate.getForObject("http://localhost:8004/customers/getlist/"+phoneNo, List.class));

	}

}
