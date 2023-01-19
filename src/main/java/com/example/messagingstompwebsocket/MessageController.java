package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

	@MessageMapping("/message")
	@SendTo("/topic/chat")
	public Message message(Message message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Message(message.getName(), message.getMessage());
	}

}
