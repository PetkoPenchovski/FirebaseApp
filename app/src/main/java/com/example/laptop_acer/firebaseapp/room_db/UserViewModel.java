package com.example.laptop_acer.firebaseapp.room_db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel{

    private UserRepository mRepository;

    private LiveData<List<UserRoomDB>> liveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
        liveData = mRepository.getAllUserRoomDB();
    }

    public LiveData<List<UserRoomDB>> getAllTowns() {
        return liveData;
    }

    public void insert(UserRoomDB note) {
        mRepository.insert(note);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
