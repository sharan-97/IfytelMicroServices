package com.infytel.plans.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infytel.plans.dto.PlanDTO;
import com.infytel.plans.entity.Plan;
import com.infytel.plans.repository.PlanRepository;
@Service
public class PlanService {
	@Autowired
	PlanRepository planRepo;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	// Fetches all plan details
	public List<PlanDTO> getAllPlans() {
		List<Plan> plans = planRepo.findAll();
		List<PlanDTO> planDTOs = new ArrayList<>();

		for (Plan plan : plans) {
			PlanDTO planDTO = PlanDTO.valueOf(plan);
			planDTOs.add(planDTO);
		}

		logger.info("Plan details : {}", planDTOs);
		return planDTOs;
	}
	
	public PlanDTO getSpecificPlan(Integer planId){
		
		Plan plan = planRepo.getReferenceById(planId);
		return PlanDTO.valueOf(plan);
		
	}

}
