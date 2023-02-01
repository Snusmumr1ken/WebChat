package com.example.messagingwebsocket;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MessageController {
	MessageRepository repository = new MessageRepository();

	public MessageController() throws SQLException {
	}

	@GetMapping("/messages")
	public ResponseEntity<List<Message>> messages() throws Exception {
		return new ResponseEntity<>(repository.getAllMessages(), HttpStatus.OK);
	}

}
