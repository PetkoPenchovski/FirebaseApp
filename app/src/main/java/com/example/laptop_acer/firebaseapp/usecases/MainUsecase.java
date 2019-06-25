package com.example.laptop_acer.firebaseapp.usecases;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;

import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.google.firebase.storage.StorageReference;

public class MainUsecase {

    private static final String TAG = "MainUsecase";

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ViewListener viewListener;
    private UserAuthRepository userAuthRepository;

    public MainUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public interface ViewListener {
    }
}
