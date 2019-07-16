package com.example.laptop_acer.firebaseapp.usecases;

import android.util.Log;

import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.repositories.UserAuthRepository;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;

public class LoginUsecase {

    private static final String TAG = "LoginUsecase";

    private UserAuthRepository.SignInListener signInListener;
    private UserAuthRepository userAuthRepository;
    private ViewListener viewListener;

    public LoginUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void signIn(String email, final String password) {
        viewListener.showProgress();
        userAuthRepository.addSignInListener(getSignInListener());
        userAuthRepository.loginUserByEmail(email, password);
    }

    private UserAuthRepository.SignInListener getSignInListener() {
        return new UserAuthRepository.SignInListener() {
            @Override
            public void onSignInSuccessful(String userId) {

                Log.e(TAG, "onSignInSuccessful:");
                viewListener.hideProgress();
                viewListener.showLoginSuccess();
            }

            @Override
            public void onSignInError() {
                Log.e(TAG, "onSignInError: ");
                viewListener.hideProgress();
                viewListener.showInvalidEmail();
            }

            @Override
            public void showInvalidPasswordError() {
                Log.e(TAG, "showInvalidPasswordError:");
                viewListener.hideProgress();
                viewListener.showInvalidPassword();
            }
        };
    }

    public void checkForLoggedUser() {
        userAuthRepository.addCheckUserListener(new UserAuthRepository.CheckUserListener() {
            @Override
            public void userLoggedSuccessful() {
                viewListener.showLoginSuccess();
            }

            @Override
            public void userLoggedError() {
            }
        });
    }

    public void validateUserData(String email, String password) {
        if (ValidatorUtils.isValidateEmail(email) && ValidatorUtils.isValidatePassword(password)) {
            signIn(email, password);
        } else if (!ValidatorUtils.isValidateEmail(email)) {
            viewListener.showInvalidEmail();
        } else if (!ValidatorUtils.isValidatePassword(password)) {
            viewListener.showInvalidPassword();

        }

    }

    public interface ViewListener {

        void hideProgress();

        void showProgress();

//        void showLoginFailed();

        void showInvalidEmail();

        void showInvalidPassword();

        void showLoginSuccess();
    }
}
