package com.ako.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class MessageUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(name = "user_id")
	int userId;
	int groupId;
	int messageUserTypeId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public MessageUserType getMessageUserTypeId() {
		return MessageUserType.getType(this.messageUserTypeId);
	}
	public void setMessageUserTypeId(MessageUserType messageUserTypeId) {
		this.messageUserTypeId = messageUserTypeId.getType();
	}
	public int getId() {
		return id;
	}
}
