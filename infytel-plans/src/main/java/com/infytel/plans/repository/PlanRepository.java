package com.infytel.plans.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.infytel.plans.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	


}
