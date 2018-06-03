package com.jules.tocapp;

import java.util.ArrayList;
import java.util.List;

public class Conversation {

    public String with;
    public List<Message> messages;

    public Conversation(String with, Message firstMessage) {
        this.with = with;
        messages = new ArrayList<>();
        addMessageToList(firstMessage);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getWith() {
        return with;
    }

    public void addMessageToList(Message message)
    {
        messages.add(message);
    }
}
