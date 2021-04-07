package com.example.mqttchat.model;

import java.io.Serializable;

public class MessageModel implements Serializable {
    private String message;
    private String timestamp;

    public MessageModel(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
