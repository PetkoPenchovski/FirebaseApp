package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "userRoomDB")
public class UserRoomDB {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "email_address")
    private String email;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "pass")
    private String password;

    public UserRoomDB(@NonNull String userName,@NonNull String email,@NonNull String phoneNumber,
                      @NonNull String password) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }




}
