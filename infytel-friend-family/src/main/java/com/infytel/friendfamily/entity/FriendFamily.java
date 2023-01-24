package com.infytel.friendfamily.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friendfamily")
public class FriendFamily {
   
	
// there is some issue with this automatic id generation man, 
	//hence have downgraded the version to use javax instead of jakarta
	@Id
	@GeneratedValue
	int id;

	@Column(name = "phone_no")
	long phoneNo;

	@Column(name = "friend_and_family")
	long friendAndFamily;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public long getFriendAndFamily() {
		return friendAndFamily;
	}

	public void setFriendAndFamily(long friendAndFamily) {
		this.friendAndFamily = friendAndFamily;
	} 

	public FriendFamily() {
		super();
	}

}
