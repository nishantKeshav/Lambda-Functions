package com.example;

// This is a simple POJO (Plain Old Java Object) for our response.
public class LambdaResponse {
    private String message;
    private String timestamp;

    // Constructors, Getters, and Setters are needed
    public LambdaResponse(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}