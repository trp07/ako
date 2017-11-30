package com.ako.data;

import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.mail.SimpleMailMessage;

@Entity
public class Message {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createDate;
	
	@OneToOne(optional = false)
	@JoinColumn(name="id")
	private Course course;
	
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String body;
	
	@OneToOne(optional = true)
	@JoinColumn(name="id")
	private Message previousMessage;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Message_user", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<MessageUser> messageUsers; // TODO
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Message_file", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"))
	private List<File> messageFile;

	// private boolean sent;
	
	public int getId() {
		return id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public Course getCourse(){
		return course;
	}
	public void setCourse(Course course){
		this.course = course;
	}
	
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
	
	public int getPreviousMessageId() {
		return id;
	}
	
	public void setPreviousMessageId(Message previousMessage) {
		this.previousMessage = previousMessage;
	}

//	private boolean result;
//
//	public void setResult(boolean result) {
//		this.result = result;
//	}
//
//	public boolean isResult() {
//		return result;
//	}

	/*public void setSent(boolean sent) {
		this.sent = sent;
	}

	public boolean isSent() {
		return sent;
	}*/
	
	// I believe we need get these users from the MessageUser data
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
