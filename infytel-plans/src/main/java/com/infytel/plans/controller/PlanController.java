package com.infytel.plans.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infytel.plans.dto.PlanDTO;
import com.infytel.plans.service.PlanService;

@RestController
@CrossOrigin
public class PlanController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PlanService planService;

	// Fetches all plan details
	@GetMapping(value = "/plans", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PlanDTO> getAllPlans() {
		return planService.getAllPlans();
	}
	// to fetch a specific plan bro
	@GetMapping(value = "/plans/{planId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PlanDTO getSpecificPlan(@PathVariable Integer planId) {
		

		return planService.getSpecificPlan(planId);
	}

	}
