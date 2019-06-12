package com.example.laptop_acer.firebaseapp.room_db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mUserRepository;

    private LiveData<List<UserDB>> mAllUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mAllUsers = mUserRepository.getAllUserRoomDB();

    }

    public LiveData<List<UserDB>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(UserDB userDB) {
        mUserRepository.insert(userDB);
    }
}
