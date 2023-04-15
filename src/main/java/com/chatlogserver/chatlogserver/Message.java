package com.chatlogserver.chatlogserver;

import java.util.*;


class Message {

    private final String messageId;
    private final String message;
    private final long timestamp;
    private final boolean isSent;

    public Message(String message, long timestamp, boolean isSent) {
        this.messageId =UUID.randomUUID().toString();
        this.message = message;
        this.timestamp = timestamp;
        this.isSent = isSent;
    }

    public Message(String messageId) {
        this.messageId = messageId;
        this.message = null;
        this.timestamp = 0;
        this.isSent = false;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isSent() {
        return isSent;
    }

}
