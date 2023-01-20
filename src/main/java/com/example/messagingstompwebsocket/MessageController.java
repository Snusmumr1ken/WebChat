package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MessageController {
	@Autowired
	MessageRepository repository;

	@MessageMapping("/message")
	@SendTo("/topic/chat")
	public Message message(Message message) throws Exception {
		repository.saveMessage(message.name(), message.message(), message.timestamp());
		System.out.println(repository.getAllMessages());
		return new Message(message.name(), message.message(), message.timestamp());
	}

	@GetMapping("/messages")
	public ResponseEntity<List<Message>> messages() throws Exception {
		return new ResponseEntity<>(repository.getAllMessages(), HttpStatus.OK);
	}

}
