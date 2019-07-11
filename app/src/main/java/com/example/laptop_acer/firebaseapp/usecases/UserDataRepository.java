package com.example.laptop_acer.firebaseapp.usecases;

public interface UserDataRepository {

    void addUser(String id, String username, String phone, String email, DataListener<Void> listener);

//    interface UserDataListener {
//        void saveSuccess();
//    }
}
