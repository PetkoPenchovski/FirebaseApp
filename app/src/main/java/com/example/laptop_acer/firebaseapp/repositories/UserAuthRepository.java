package com.example.laptop_acer.firebaseapp.repositories;

public interface UserAuthRepository {

    void registerUser(String email, String password, String username, String phoneNumber);

    void loginUserByEmail(String email, String password);

    void addSignInListener(SignInListener signInListener);

    void addSignUpListener(SignUpListener signUpListener);

    void addCheckUserListener(CheckUserListener checkUserListener);

    interface SignInListener {
        void onSignInSuccessful(String userId);

        void onSignInError();

        void showInvalidPasswordError();
    }

    interface SignUpListener {
        void onSignUpSuccessful(String userId);

        void onSignUpError(String errorMessage);

        void onSignUpError();
    }

    interface CheckUserListener {
        void userLoggedSuccessful();

        void userLoggedError();
    }
}
