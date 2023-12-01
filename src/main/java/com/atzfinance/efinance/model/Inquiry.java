package com.atzfinance.efinance.model;

import java.time.LocalDateTime;

public class Inquiry {
    private int inquiryID;
    private String name;
    private String email;
    private String message;
    private LocalDateTime timestamp; // Use java.time.LocalDateTime for timestamp

    public void setTimestamp(LocalDateTime now) {
    }

    // getters and setters
    public int getInquiryID() {
        return inquiryID;
    }

    public void setInquiryID(int inquiryID) {
        this.inquiryID = inquiryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}

