package com.example.laptop_acer.firebaseapp.room_db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.laptop_acer.firebaseapp.model.User;

import java.util.List;

public class UserRepository {

    private UserRoomDBDAO mUserRoomDBDAO;
    private LiveData<List<UserDb>> mAllUsers;
    private LiveData<UserDb> userDbLiveData;

    UserRepository(Application application, String id) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserRoomDBDAO = db.userRoomDBDAO();
        mAllUsers = mUserRoomDBDAO.getAllUserRoomDB();
        userDbLiveData = mUserRoomDBDAO.getUserById(id);
    }

    LiveData<List<UserDb>> getAllUserRoomDB() {
        return mAllUsers;
    }

    public LiveData<UserDb> getUserById(String id) {
        return userDbLiveData;
    }

    public void insert(UserDb userDb) {
        new insertAsyncTask(mUserRoomDBDAO).execute(userDb);
    }

    private static class insertAsyncTask extends AsyncTask<UserDb, Void, Void> {

        private UserRoomDBDAO mAsyncTaskDao;

        insertAsyncTask(UserRoomDBDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserDb... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
