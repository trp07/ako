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

import com.ako.data.IMessageUserRepository;
import com.ako.data.Message;
import com.ako.data.MessageRepository;
import com.ako.data.MessageUser;

@Component
@Service
public class MessageService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private IMessageUserRepository  messageUserRepository;

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	/**
	 * Fetch all the users
	 * 
	 * @return Return a list of all users
	 */

	public List<Message> getAllMessages() {
		List<Message> messages = new ArrayList<>();
		this.messageRepository.findAll().forEach(messages::add);
		return messages;
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
	 * @param Message
	 */
	@Async
	private void sendMessage(Message Message) {

		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo("vishakha@umd.edu");
			mail.setFrom("prashant@gmail.com");
			mail.setSubject(Message.getSubject());
			mail.setText(Message.getBody());

			this.javaMailSender.send(mail);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// save to database
			// this.messageRepository.save(Message);
		}
	}

	public void createMessage(Message message) {
		Message createdMessage = this.messageRepository.save(message);
		List<MessageUser> messageUsers = message.getMessageUsers();
		for (MessageUser messageUser : messageUsers) {
			
			messageUser.setMessageId(createdMessage.getId());
		}
		this.messageUserRepository.save(messageUsers);
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
