package com.example.laptop_acer.firebaseapp.user;

public class User {

    private String userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userPassword;

    public User(String userId, String userName, String userEmail, String userPhoneNumber
            , String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
