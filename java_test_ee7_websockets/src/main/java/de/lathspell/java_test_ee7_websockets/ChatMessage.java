package de.lathspell.java_test_ee7_websockets;

import java.util.Date;

public class ChatMessage {

    private String message;

    private Date received;

    @Override
    public String toString() {
        return "ChatMessage{" + "message=" + message + ", received=" + received + '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

}
