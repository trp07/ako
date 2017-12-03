package com.ako.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ako.data.Message;
import com.ako.service.MessageService;



@RestController
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

	@Autowired
	MessageService messageService;

	@RequestMapping("/all/{id}")
	public List<Message> getAllMessages(@PathVariable int id) {
		return messageService.getAllMessages(id);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/send")
	public void send(@RequestBody Message email) {
		this.messageService.createMessage(email);
	}

	@RequestMapping("/{id}")
	public Message getMessage(@PathVariable int id) {
		return this.messageService.getMessage(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/save")
	public void saveMessage(@RequestBody Message message){
		messageService.saveMessage(message);
	}
	/*
	 * @RequestMapping("/send") public void getAll() {
	 * this.messageService.sendMessage(email); }
	 */

}