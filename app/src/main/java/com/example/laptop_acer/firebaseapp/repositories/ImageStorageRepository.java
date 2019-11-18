package com.example.laptop_acer.firebaseapp.repositories;

import com.example.laptop_acer.firebaseapp.usecases.DataListener;

public interface ImageStorageRepository {

    void uploadImage(byte[] bytes, String name, DataListener<String> listener);
}
