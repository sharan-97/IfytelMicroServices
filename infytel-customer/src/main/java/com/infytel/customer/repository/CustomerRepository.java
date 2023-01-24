package com.infytel.customer.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.infytel.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
}
