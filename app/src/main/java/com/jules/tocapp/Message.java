package com.jules.tocapp;

import java.util.Date;

public class Message {

    String from;
    String text;
    Date time;
    boolean read;

    public Message(){};

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
        this.time = Util.getCurrentTime();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }



}
