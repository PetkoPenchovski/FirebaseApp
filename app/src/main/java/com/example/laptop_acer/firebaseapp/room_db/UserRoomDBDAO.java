package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserRoomDBDAO {
    @Insert
    void insert(UserDB userDB);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY username ASC")
    LiveData<List<UserDB>> getAllUserRoomDB();
}

