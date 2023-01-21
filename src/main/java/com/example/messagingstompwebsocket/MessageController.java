package com.example.messagingstompwebsocket;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
public class MessageController {
	MessageRepository repository = new MessageRepository();

	public MessageController() throws SQLException {
	}

	@MessageMapping("/message")
	@SendTo("/topic/chat")
	public Message message(Message message) throws Exception {
		if (!Objects.equals(message.message(), "left chat.") && !Objects.equals(message.message(), "connected.")) {
			repository.saveMessage(message.name(), message.message(), message.timestamp());
		}
		return new Message(message.name(), message.message(), message.timestamp());
	}

	@GetMapping("/messages")
	public ResponseEntity<List<Message>> messages() throws Exception {
		return new ResponseEntity<>(repository.getAllMessages(), HttpStatus.OK);
	}

}
