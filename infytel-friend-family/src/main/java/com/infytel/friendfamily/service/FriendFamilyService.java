package com.infytel.friendfamily.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infytel.friendfamily.dto.FriendFamilyDTO;
import com.infytel.friendfamily.entity.FriendFamily;
import com.infytel.friendfamily.repository.FriendFamilyRepository;

@Service
public class FriendFamilyService {

	@Autowired
	FriendFamilyRepository friendFamilyRepository;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void SaveFriemdsDetails(Long phoneNo, FriendFamilyDTO friendDTO) {

		friendDTO.setPhoneNo(phoneNo);
		FriendFamily friendFamily = friendDTO.createFriend();
		logger.info("checking friend family object",friendFamily );
		friendFamilyRepository.save(friendFamily);


	}

	public List<Long> getForSpecificNumber(Long phoneNo){
		logger.info("inside Friend service class");

		List<FriendFamily> friends = friendFamilyRepository.findByPhoneNo(phoneNo);
		List<Long> friendsList = new ArrayList<>();
		for(FriendFamily friend : friends ) {
			friendsList.add(friend.getFriendAndFamily());
		}

		return friendsList;

	}



}
