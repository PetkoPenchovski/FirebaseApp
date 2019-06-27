package com.example.laptop_acer.firebaseapp.remote;

import com.example.laptop_acer.firebaseapp.model.User;
import com.example.laptop_acer.firebaseapp.usecases.UserDataRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataRepository implements UserDataRepository {

    private DatabaseReference databaseUsers;
    private UserDataListener userDataListener;
    private static final String FIREBASE_TABLE_USERS = "users";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_NAME = "userName";
    private static final String USER_PHONE = "userPhoneNumber";

    public FirebaseDataRepository() {
        databaseUsers = FirebaseDatabase.getInstance().getReference(FIREBASE_TABLE_USERS);
    }

    public void updateUser(String username, String email, String phone) {
        String userId = FirebaseAuthRepository.getInstance().getUserId();
        databaseUsers.child(userId).child(USER_EMAIL).setValue(email);
        databaseUsers.child(userId).child(USER_NAME).setValue(username);
        databaseUsers.child(userId).child(USER_PHONE).setValue(phone);

    }

    @Override
    public void addUser(String id, String username, String phone, String email) {

        User user = new User(id, username, phone, email);
        databaseUsers.child(id).setValue(user);

        userDataListener.saveSuccess();
    }

    @Override
    public void addUserDataListener(UserDataListener userDataListener) {
        this.userDataListener = userDataListener;
    }
}
