package com.ako.service;

import static org.mockito.Mockito.verifyNoMoreInteractions;

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

import com.ako.data.Email;
import com.ako.data.MessageRepository;
import com.ako.data.MessageUser;
import com.ako.data.User;

@Component
@Service
public class MessageService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private MessageRepository messageRepository;

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	
	/**
	 * Fetch all the users
	 * 
	 * @return Return a list of all users
	 */
	
	public List<Email> getAllMessages() {
		List<Email> messages = new ArrayList<>();
		this.messageRepository.findAll().forEach(messages::add);
		return messages;
	}
	
	/**
	 * Fetch message by id
	 * 
	 * @param id
	 * @return The message identified by given id
	 */
	public Email getMessage(int id) {
		return this.messageRepository.findOne(id);
	}
	
	/**
	 * simple send text message
	 * 
	 * @param email
	 */
	@Async
	public void sendMessage(Email email) {

		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo("vishakha@umd.edu");
			mail.setFrom("prashant@gmail.com");
			mail.setSubject(email.getSubject());
			mail.setText(email.getBody());

			this.javaMailSender.send(mail);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// save to database
			//this.messageRepository.save(email);
		}
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
