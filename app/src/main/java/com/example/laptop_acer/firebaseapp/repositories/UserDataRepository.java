package com.example.laptop_acer.firebaseapp.repositories;

import com.example.laptop_acer.firebaseapp.usecases.DataListener;

public interface UserDataRepository {

    void addUser(String id, String username, String phone, String email, DataListener<Void> listener);
}
