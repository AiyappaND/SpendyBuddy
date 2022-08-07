package com.example.spendybuddy.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserDetails {

    private String userId;
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    private String emailId;


    public UserDetails(String userId, String fullName, String emailId) {
        this.userId = userId;
        this.fullName = fullName;
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

}