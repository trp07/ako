package com.ako.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.springframework.mail.SimpleMailMessage;

@Entity
public class Email {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	private String subject;
	private String body;
	private String previousMessageId;
	private String courseId;
	private String createDate;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "MessageUser", joinColumns = @JoinColumn(name = "messageId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	private List<MessageUser> messageUsers;

	// private boolean sent;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getId() {
		return id;
	}

	private boolean result;

	public void setResult(boolean result) {
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}

	/*public void setSent(boolean sent) {
		this.sent = sent;
	}

	public boolean isSent() {
		return sent;
	}*/

	public MessageUser[] getToUsers() {
		return this.messageUsers.stream().filter(u -> u.getMessageUserTypeId() == MessageUserType.TO)
				.toArray(MessageUser[]::new);
	}

	public MessageUser[] getCcUsers() {
		return this.messageUsers.stream().filter(u -> u.getMessageUserTypeId() == MessageUserType.CC)
				.toArray(MessageUser[]::new);
	}

	public MessageUser[] getBccUsers() {
		return this.messageUsers.stream().filter(u -> u.getMessageUserTypeId() == MessageUserType.BCC)
				.toArray(MessageUser[]::new);
	}

	public MessageUser getFromUsers() {
		return this.messageUsers.stream().filter(u -> u.getMessageUserTypeId() == MessageUserType.FROM).findFirst()
				.get();
	}
	
	public List<MessageUser> getMessageUsers() {
		return messageUsers;
	}

}
