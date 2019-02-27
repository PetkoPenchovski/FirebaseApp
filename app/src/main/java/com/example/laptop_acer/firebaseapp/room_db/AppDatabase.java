package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserRoomDB.class}, version = 1, exportSchema = false)
abstract public class AppDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();

}
