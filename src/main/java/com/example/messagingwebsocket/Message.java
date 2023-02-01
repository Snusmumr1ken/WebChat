package com.example.messagingwebsocket;

public class Message {
    public String name;
    public String message;
    public long timestamp;

    public Message(String user_name, String message, long sent_time) {
        this.name = user_name;
        this.message = message;
        this.timestamp = sent_time;
    }
}
