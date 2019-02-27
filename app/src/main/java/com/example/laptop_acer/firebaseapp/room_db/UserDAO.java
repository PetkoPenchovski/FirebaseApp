package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface UserDAO {
    @Insert
    void insert(UserRoomDB userRoomDB);

    @Update
    void update(UserRoomDB userRoomDB);

    @Delete
    void delete(UserRoomDB userRoomDB);

    @Query("SELECT * FROM userRoomDB where email= :email and password= :password")
    UserRoomDB getUserRoomDB(String email, String password);
}

