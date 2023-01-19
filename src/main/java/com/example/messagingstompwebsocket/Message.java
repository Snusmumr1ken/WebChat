package com.example.messagingstompwebsocket;

public class Message {

	private String message;
	private String name;

	public Message() {
	}

	public Message(String name, String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}
}
