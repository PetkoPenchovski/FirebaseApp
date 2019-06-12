package com.example.laptop_acer.firebaseapp.usecases;

import android.util.Log;

import com.example.laptop_acer.firebaseapp.model.User;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.room_db.UserDB;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;
import com.google.firebase.database.DatabaseReference;

public class RegistrationUsecase {

    private static final String TAG = "LoginUsecase";

    private DatabaseReference databaseReference;
    private UserAuthRepository userAuthRepository;
    private UserDataRepository userDataRepository;
    private ViewListener viewListener;
    private String username;
    private User user;


    public RegistrationUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
        userDataRepository = new FirebaseDataRepository();
        this.user = new User();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }


    public void register(String email, String password, String username, String phoneNumber) {
        Log.e(TAG, "Registered user " + email);
        viewListener.showProgress();
        userAuthRepository.addSignUpListener(getSignUpListener());
        userAuthRepository.registerUser(email, password, username, phoneNumber);
        buildUser(email, username, phoneNumber);
        this.username = username;
        userDataRepository.addUserDataListener(getUserDataListener());
    }

    private void buildUser(String email, String username, String phoneNumber) {
        user.setUserEmail(email);
        user.setUserName(username);
        user.setUserPhoneNumber(phoneNumber);
    }


    private UserDataRepository.UserDataListener getUserDataListener() {
        return new UserDataRepository.UserDataListener() {
            @Override
            public void saveSuccess() {
                UserDB userDB = new UserDB(user.getUserId(), user.getUserName(), user.getUserEmail(),
                        user.getUserPhoneNumber());
                viewListener.addUserToLocalDb(userDB);
                viewListener.showMainScreen(username);
            }
        };
    }

    private UserAuthRepository.SignUpListener getSignUpListener() {
        return new UserAuthRepository.SignUpListener() {
            @Override
            public void onSignUpSuccessful(String userId) {

                Log.e(TAG, "onSignUpSuccessful:");
                viewListener.hideProgress();
                user.setUserId(userId);
                addUser(user);
            }

            @Override
            public void onSignUpError(String errorMessage) {
                Log.e(TAG, "onSignUpError: ");
                viewListener.hideProgress();
                viewListener.showSignUpFailed(errorMessage);
            }

            @Override
            public void onSignUpError() {
                Log.e(TAG, "onSignUpError: ");
                viewListener.hideProgress();
                viewListener.showSingUpFailed();
            }

        };
    }

    public void addUser(User user) {
        userDataRepository.addUser(user.getUserId(), user.getUserName(), user.getUserPhoneNumber(),
                user.getUserEmail());
    }

    public void validateNewUserData(String email, String password, String username,
                                    String phoneNumber, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            if (ValidatorUtils.validateEmail(email) && ValidatorUtils.validatePassword(password)
                    && ValidatorUtils.validateName(username) && ValidatorUtils.validatePhone(phoneNumber)) {
                register(email, password, username, phoneNumber);
            } else if (!ValidatorUtils.validateText(username)) {
                viewListener.showInvalidRegUsername();
            } else if (!ValidatorUtils.validateEmail(email)) {
                viewListener.showInvalidRegEmail();
            } else if (!ValidatorUtils.validatePhone(phoneNumber)) {
                viewListener.showInvalidRegPhoneNumber();
            } else if (!ValidatorUtils.validatePassword(password)) {
                viewListener.showInvalidRegPassword();
            }
        } else {
            viewListener.showPasswordMismatch();
        }
    }


    public interface ViewListener {

        void showSingUpFailed();

        void showSignUpFailed(String errorMessage);

        void showProgress();

        void hideProgress();

        void showInvalidRegEmail();

        void showInvalidRegPassword();

        void showInvalidRegUsername();

        void showInvalidRegPhoneNumber();

        void showMainScreen(String username);

        void showPasswordMismatch();

        void addUserToLocalDb(UserDB userDB);

        void onUserIdReceived(String id);

    }
}
