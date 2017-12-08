package com.ako.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ako.core.AkoUtility;
import com.ako.data.IMessageUserRepository;
import com.ako.data.Message;
import com.ako.data.IMessageRepository;
import com.ako.data.MessageUser;

@Component
@Service
public class MessageService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private IMessageRepository messageRepository;
	
	@Autowired
	private IMessageUserRepository  messageUserRepository;

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	/**
	 * Fetch all the users
	 * 
	 * @return Return a list of all users
	 */

	public List<Message> getAllMessages(int id) {
		List<Message> messages = new ArrayList<>();
		List<Message> messages1 = this.messageRepository.findByUserId(id);
		this.messageRepository.findByUserId(id).forEach(messages::add);
		return messages1;
	}

	/**
	 * Fetch message by id
	 * 
	 * @param id
	 * @return The message identified by given id
	 */
	public Message getMessage(int id) {
		return this.messageRepository.findOne(id);
	}

	/**
	 * save message by id
	 * 
	 * @param id
	 * @return The message identified by given id
	 */

	public void saveMessage(Message message) {
		this.messageRepository.save(message);
	}

	/**
	 * delete message by id
	 * 
	 * @param id
	 * @return The message identified by given id
	 */
	public void deleteMessage(int id) {
		this.messageRepository.delete(id);
	}

	/**
	 * simple send text message
	 * 
	 * @param message
	 */
	@Async
	private void sendMessage(Message message) {

		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			
			List<String> userEmails = new ArrayList<String>();
			for (MessageUser messageUser : message.getToUsers()) {
				userEmails.add(userService.getUser(messageUser.getUserId()).getEmail());
			}
			
			String[] toUsers = new String[userEmails.size()];
			userEmails.toArray(toUsers);
			
			String from = userService.getUser(message.getFromUsers().getUserId()).getEmail();
			
			mail.setTo(toUsers);
			mail.setFrom(from);
			mail.setSubject(message.getSubject());
			mail.setText(message.getBody());

			this.javaMailSender.send(mail);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// save to database
			// this.messageRepository.save(Message);
		}
	}
	
	public Message updateUser(Message message) {
		//spring JPA does not let us update partial object
		//fetch the existing from db and update the existing object instead
		Message existing = this.messageRepository.findOne(message.getId());
		AkoUtility.copyNonNullProperties(message, existing);
		
		return this.messageRepository.save(existing);
	}
	
	public void createMessage(Message message) {
		Message createdMessage = this.messageRepository.save(message);
		List<MessageUser> messageUsers = message.getMessageUsers();
		for (MessageUser messageUser : messageUsers) {
			
			messageUser.setMessageId(createdMessage.getId());
		}
		this.messageUserRepository.save(messageUsers);
		this.sendMessage(message);
	}
	
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		try {
			MimeMessage message = this.javaMailSender.createMimeMessage();
			// pass 'true' to the constructor to create a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);

			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);

			this.javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
