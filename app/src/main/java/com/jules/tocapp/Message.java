package com.jules.tocapp;

import java.time.LocalDateTime;
import java.util.Date;

public class Message {

    String from;
    String text;
    boolean isReaded;

    public Message(String from, String text, boolean isReaded) {
        this.from = from;
        this.text = text;
        this.isReaded = isReaded;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public boolean isReaded() {
        return isReaded;
    }
    public void setReaded(boolean readed) {
        isReaded = readed;
    }

}
