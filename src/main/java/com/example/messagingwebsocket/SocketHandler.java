package com.example.messagingwebsocket;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    MessageRepository repository;

    {
        try {
            repository = new MessageRepository();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public SocketHandler() {
    }

    private void saveTextMessage(TextMessage message) {
        // parse json
        Message m = new Gson().fromJson(message.getPayload(), Message.class);

        // try to save
        if (!Objects.equals(m.message, "left chat.") && !Objects.equals(m.message, "connected.")) {
            try {
                repository.saveMessage(m.name, m.message, m.timestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        saveTextMessage(message);

        for (WebSocketSession webSocketSession: sessions) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
