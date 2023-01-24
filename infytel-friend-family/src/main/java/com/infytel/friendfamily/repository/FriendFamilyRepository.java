package com.infytel.friendfamily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infytel.friendfamily.entity.FriendFamily;


public interface FriendFamilyRepository extends JpaRepository<FriendFamily, Integer>{

	List<FriendFamily> findByPhoneNo(long phoneNo);
}
