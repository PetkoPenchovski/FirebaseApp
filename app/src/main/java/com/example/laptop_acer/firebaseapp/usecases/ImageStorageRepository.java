package com.example.laptop_acer.firebaseapp.usecases;

public interface ImageStorageRepository {

    void uploadImage(byte[] bytes, String name, DataListener<Boolean> listener);

}
