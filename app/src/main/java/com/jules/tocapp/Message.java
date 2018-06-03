package com.jules.tocapp;

import java.util.Date;

public class Message {

    String from;
    String text;
    Date time;
    boolean isRead;

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
        this.time = Util.getCurrentTime();
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }



}
