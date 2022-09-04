package com.tutorial.response;

import java.io.Serializable;

public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 1146539469456398247L;

    public MessageResponse(String message) {
        super();
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
