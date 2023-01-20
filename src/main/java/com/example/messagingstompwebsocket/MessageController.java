package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	@Autowired
	MessagesRepository repository;

	@MessageMapping("/message")
	@SendTo("/topic/chat")
	public Message message(Message message) throws Exception {
		repository.saveMessage(message.name(), message.message(), message.timestamp());
		System.out.println(repository.getAllMessages());
		return new Message(message.name(), message.message(), message.timestamp());
	}

}
