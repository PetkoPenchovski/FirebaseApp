package com.example.laptop_acer.firebaseapp.room_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserDb.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserRoomDBDAO userRoomDBDAO();
    private static final String ROOM_DB_NAME = "user_database";

    private static UserRoomDatabase INSTANCE;

    static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, ROOM_DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
