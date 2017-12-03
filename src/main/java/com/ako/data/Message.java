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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.mail.SimpleMailMessage;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createDate;
	
	//@OneToOne(optional = false)
	//@JoinColumn(name="courseId")
	//private Course course;

	@Column(nullable = false)
	private int courseId;
	
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String body;
	

	private Integer previousMessageId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "messageId")
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
	
	//@JsonIgnore
	//public Course getCourse(){
	//	return course;
	//}
	//public void setCourse(Course course){
	//	this.course = course;
	//}
	
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
	
	public Integer getPreviousMessageId() {
		return previousMessageId;
	}
	
	public void setPreviousMessageId(Integer previousMessage) {
		this.previousMessageId = previousMessage;
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

}
