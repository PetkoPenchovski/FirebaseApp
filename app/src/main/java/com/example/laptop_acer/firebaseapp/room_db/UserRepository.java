package com.example.laptop_acer.firebaseapp.room_db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {

    private UserDAO userDAO;
    private LiveData<List<UserRoomDB>> liveData;

    UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.mUserDAO();
        liveData = userDAO.getAllUserRoomDB();
    }

    LiveData<List<UserRoomDB>> getAllUserRoomDB() {
        return liveData;
    }

    public void insert (UserRoomDB userRoomDB) {
        new InsertAsyncTask(userDAO).execute(userRoomDB);
    }

    public void deleteAll() {
        new DeleteAsyncTask(userDAO).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<UserRoomDB, Void, Void> {

        private UserDAO mAsyncTaskDao;

        InsertAsyncTask(UserDAO userDAO) {
            mAsyncTaskDao = userDAO;
        }

        @Override
        protected Void doInBackground(final UserRoomDB... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDAO mAsyncTaskDao;

        DeleteAsyncTask(UserDAO userDAO) {
            this.mAsyncTaskDao = userDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
