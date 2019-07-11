package com.example.laptop_acer.firebaseapp.usecases;

import android.util.Log;

import com.example.laptop_acer.firebaseapp.model.User;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;

public class RegistrationUsecase {

    private static final String TAG = "LoginUsecase";
    private UserAuthRepository userAuthRepository;
    private TaskDataRepository taskDataRepository;
    private UserDataRepository userDataRepository;
    private ViewListener viewListener;
    private String username;
    private User user;

    public RegistrationUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
        taskDataRepository = new FirebaseDataRepository();
        userDataRepository = new FirebaseDataRepository();
        this.user = new User();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void register(String email, String password, String username, String phoneNumber) {
        viewListener.showProgress();
        userAuthRepository.addSignUpListener(getSignUpListener());
        userAuthRepository.registerUser(email, password, username, phoneNumber);
        buildUser(email, username, phoneNumber);
        this.username = username;
    }

    private void buildUser(String email, String username, String phoneNumber) {
        user.setUserEmail(email);
        user.setUserName(username);
        user.setUserPhoneNumber(phoneNumber);
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

    public void addUser(final User user) {
        userDataRepository.addUser(user.getUserId(), user.getUserName(), user.getUserPhoneNumber(),
                user.getUserEmail(), new DataListener<Void>() {
                    @Override
                    public void onDataSuccess(Void data) {
                        UserDb userDb = new UserDb(user.getUserId(), user.getUserName(),
                                user.getUserEmail(), user.getUserPhoneNumber());
                        viewListener.addUserToLocalDb(userDb);
                        viewListener.showMainScreen(username);
                    }

                    @Override
                    public void onDataError(String message) {
                        Log.e(TAG, "onDataError: Saving user with username: " + username
                                + " remotely failed");
                        viewListener.showMainScreen(username);
                    }
                });
    }

    public void validateNewUserData(String email, String password, String username,
                                    String phoneNumber, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            if (ValidatorUtils.isValidateEmail(email) && ValidatorUtils.isValidatePassword(password)
                    && ValidatorUtils.isValidateName(username) && ValidatorUtils.isValidatePhone(phoneNumber)) {
                register(email, password, username, phoneNumber);
            } else if (!ValidatorUtils.isValidateText(username)) {
                viewListener.showInvalidRegUsername();
            } else if (!ValidatorUtils.isValidateEmail(email)) {
                viewListener.showInvalidRegEmail();
            } else if (!ValidatorUtils.isValidatePhone(phoneNumber)) {
                viewListener.showInvalidRegPhoneNumber();
            } else if (!ValidatorUtils.isValidatePassword(password)) {
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

        void addUserToLocalDb(UserDb userDb);

        void onUserIdReceived(String id);

    }
}
