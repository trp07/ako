package com.ako.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ako.data.Message;
import com.ako.service.MessageService;

@Controller
@RequestMapping(value = "/messages", produces = MediaType.ALL_VALUE)
public class MessageController {

	@Autowired
	MessageService messageService;

	@RequestMapping("/")
	public List<Message> getAllMessages() {
		return messageService.getAllMessages();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/send")
	public void send(Message email) {
		this.messageService.sendMessage(email);
	}

	@RequestMapping("/{id}")
	public Message getMessage(@PathVariable int id) {
		return this.messageService.getMessage(id);
	}

	/*
	 * @RequestMapping("/send") public void getAll() {
	 * this.messageService.sendMessage(email); }
	 */

}