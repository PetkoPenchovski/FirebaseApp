package com.example.laptop_acer.firebaseapp.usecases;

public interface UserDataRepository {

    void addUser(String username, String phone, String email);

    void addUserDataListener(UserDataListener storeUserListener);

    interface UserDataListener {
        void saveSuccess();
    }
}
