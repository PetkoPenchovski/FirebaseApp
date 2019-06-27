package com.example.laptop_acer.firebaseapp.usecases;

public interface DataListener<T> {

    void onDataSuccess(T data);

    void onDataError(String message);
}
