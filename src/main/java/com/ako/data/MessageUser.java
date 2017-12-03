package com.ako.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MessageUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "user_id")
	int userId;
	
	@OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	User user;
	
	
	@Column(name = "message_id")
	int messageId;
	
	Integer groupId;
	int messageUserTypeId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@JsonIgnore
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
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
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
