package com.example.spendybuddy.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String email;

    public LoggedInUser(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}