package com.example.laptop_acer.firebaseapp.room_db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {

    private UserRoomDBDAO mUserRoomDBDAO;
    private LiveData<List<UserDB>> mAllUsers;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserRoomDBDAO = db.userRoomDBDAO();
        mAllUsers = mUserRoomDBDAO.getAllUserRoomDB();
    }

    LiveData<List<UserDB>> getAllUserRoomDB() {
        return mAllUsers;
    }


    public void insert (UserDB userDB) {
        new insertAsyncTask(mUserRoomDBDAO).execute(userDB);
    }

    private static class insertAsyncTask extends AsyncTask<UserDB, Void, Void> {

        private UserRoomDBDAO mAsyncTaskDao;

        insertAsyncTask(UserRoomDBDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserDB... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
