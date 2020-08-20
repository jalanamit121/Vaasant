package com.winbee.vasantvalleyschool.Models;

public class NotificationModel {
    String message;

    public NotificationModel() {
    }

    public NotificationModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "message='" + message + '\'' +
                '}';
    }
}