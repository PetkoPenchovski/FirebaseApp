package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;

public class AccountUsecase {

    private UserViewModel userViewModel;

    public void updateUserInRoom(UserDb userDb) {
        userViewModel.update(userDb);
    }
}
