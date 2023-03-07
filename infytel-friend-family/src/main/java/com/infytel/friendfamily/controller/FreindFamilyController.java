package com.infytel.friendfamily.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infytel.friendfamily.dto.FriendFamilyDTO;
import com.infytel.friendfamily.entity.FriendFamily;
import com.infytel.friendfamily.service.FriendFamilyService;

@RestController
@CrossOrigin
public class FreindFamilyController {

	@Autowired
	FriendFamilyService friendFamilyService;

	Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@PostMapping(value = "/customers/{phoneNo}/friends", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void saveFriend(@PathVariable Long phoneNo, @RequestBody FriendFamilyDTO friendDTO){
		
		logger.info("Creation request for customer {} with data {}", phoneNo, friendDTO);
		logger.info(" inside freind controller");
		friendFamilyService.SaveFriemdsDetails(phoneNo, friendDTO);
	}
	
	@GetMapping(value="/customers/getlist/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Long> getForSpecificNumber(@PathVariable Long phoneNo){

		System.out.println("in friend class");
		//temporary for hystrix testing
		if(phoneNo == 9009009002l) {
			
			throw new RuntimeException();
		}
		logger.info("inside friend controller");
		return friendFamilyService.getForSpecificNumber(phoneNo);
		
		
	}

}
