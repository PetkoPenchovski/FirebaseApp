package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(UserRoomDB userRoomDB);

    @Query("DELETE FROM userRoomDB")
    void deleteAll();

    @Query("SELECT * from userRoomDB ORDER BY user_name ASC")
    LiveData<List<UserRoomDB>> getAllUserRoomDB();

}

