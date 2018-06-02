package com.jules.tocapp;

import java.util.List;

public class Conversation {

    public List<Message> messages;
    public String with;

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
