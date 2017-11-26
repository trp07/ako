package com.ako.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ako.data.Email;
import com.ako.service.MessageService;

@Controller
@RequestMapping(value = "/messages", produces = MediaType.ALL_VALUE)
public class MessageController {

	@Autowired
	MessageService messageService;

	@RequestMapping("/")
	public List<Email> getAllMessages() {
		return messageService.getAllMessages();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/send")
	public void send(Email email) {
		this.messageService.sendMessage(email);
	}

	@RequestMapping("/{id}")
	public Email getMessage(@PathVariable int id) {
		return this.messageService.getMessage(id);
	}

	/*
	 * @RequestMapping("/send") public void getAll() {
	 * this.messageService.sendMessage(email); }
	 */

}