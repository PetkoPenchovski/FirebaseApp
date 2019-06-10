package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "user_table")
public class UserDB {

    @PrimaryKey
    @NonNull

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "username")
    private String userName;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @ColumnInfo(name = "password")
    private String password;

    public UserDB(int id, String userName, String email, String phoneNumber, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + userName + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phoneNumber + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }

}
