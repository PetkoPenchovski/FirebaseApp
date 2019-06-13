package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserRoomDBDAO {

    @Insert
    void insert(UserDb userDb);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY username ASC")
    LiveData<List<UserDb>> getAllUserRoomDB();

    @Query("SELECT * from user_table where id = :id LIMIT 1")
    LiveData<UserDb> getUserById(String id);
}

