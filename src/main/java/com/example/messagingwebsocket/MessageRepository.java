package com.example.messagingwebsocket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {
    Connection connection;
    Statement statement;

    MessageRepository() throws SQLException{
        // make a connection to db
        connection = DriverManager.getConnection("jdbc:sqlite:datagrok");
        statement = connection.createStatement();
        statement.setQueryTimeout(5);

        // init message table
        statement.executeUpdate("create table if not exists Message (user_name string, message string, sent_time long)");
    }

    public void saveMessage(String userName, String message, long timestamp) throws SQLException {
        String request = "insert into Message values('" + userName + "', '" + message + "', '" + timestamp + "')";
        statement.executeUpdate(request);
    }

    public List<Message> getAllMessages() throws SQLException {
        List<Message> messagesList = new ArrayList<>();
        ResultSet rs = statement.executeQuery("select * from Message");
        while(rs.next())
        {
            messagesList.add(new Message(
                                rs.getString("user_name"),
                                rs.getString("message"),
                                rs.getLong("sent_time")));
        }
        return messagesList;
    }
}
