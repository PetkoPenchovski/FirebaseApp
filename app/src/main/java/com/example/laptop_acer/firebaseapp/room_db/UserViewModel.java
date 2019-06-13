package com.example.laptop_acer.firebaseapp.room_db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mUserRepository;

    private LiveData<List<UserDb>> mAllUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application, FirebaseAuthRepository.getInstance().getUserId());
        mAllUsers = mUserRepository.getAllUserRoomDB();
    }

    public LiveData<List<UserDb>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(UserDb userDb) {
        mUserRepository.insert(userDb);
    }

    public LiveData<UserDb> getUserById(String id) {
        return mUserRepository.getUserById(id);
    }
}
