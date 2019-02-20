package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserRoomDB.class}, version = 1)
abstract public class AppDatabase extends RoomDatabase{

    public abstract UserDAO mUserDAO();

    private static AppDatabase INSTANCE;

    public AppDatabase(){

    }

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "users_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
