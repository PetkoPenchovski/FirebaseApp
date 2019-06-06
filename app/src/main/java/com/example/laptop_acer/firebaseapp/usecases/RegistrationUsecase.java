package com.example.laptop_acer.firebaseapp.usecases;

import android.util.Log;

import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;

public class RegistrationUsecase {

    private static final String TAG = "LoginUsecase";

    private UserAuthRepository userAuthRepository;
    private UserDataRepository userDataRepository;
    private ViewListener viewListener;
    private String username;


    public RegistrationUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
        userDataRepository = new FirebaseDataRepository();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }


    public void register(String email, String password, String username, String phoneNumber) {
        Log.e(TAG, "Registered user " + email);
        viewListener.showProgress();
        userAuthRepository.addSignUpListener(getSignUpListener());
        userAuthRepository.registerUser(email, password, username, phoneNumber);

        this.username = username;
        userDataRepository.addUserDataListener(getUserDataListener());
    }

    private UserDataRepository.UserDataListener getUserDataListener() {
        return new UserDataRepository.UserDataListener() {
            @Override
            public void saveSuccess() {
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
                viewListener.showSingUpSuccess();
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

    public void addUser(String username, String userPhone, String userEmail) {
        userDataRepository.addUser(username, userPhone, userEmail);
    }

    public void validateNewUserData(String email, String password, String username, String phoneNumber) {
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

    }

    public interface ViewListener {

        void showSingUpSuccess();

        void showSingUpFailed();

        void showSignUpFailed(String errorMessage);

        void showProgress();

        void hideProgress();

        void showInvalidRegEmail();

        void showInvalidRegPassword();

        void showInvalidRegUsername();

        void showInvalidRegPhoneNumber();

        void showMainScreen(String username);
    }
}
