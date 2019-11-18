package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.repositories.UserAuthRepository;

public class MainUsecase {

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
