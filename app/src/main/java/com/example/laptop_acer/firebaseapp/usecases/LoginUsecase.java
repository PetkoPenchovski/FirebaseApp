package com.example.laptop_acer.firebaseapp.usecases;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.room_db.AppDatabase;
import com.example.laptop_acer.firebaseapp.room_db.UserDAO;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUsecase {

    private static final String TAG = "LoginUsecase";

    private UserAuthRepository.SignInListener signInListener;
    private UserAuthRepository userAuthRepository;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ImageView imgViewLogin;
    private EditText edtTxtPasswordLogin;
    private EditText edtTxtEmailLogin;
    private Button btnLogin;
    private Button btnCreateRegistration;

    private AppDatabase appDatabase;
    private UserDAO userDAO;
    private ViewListener viewListener;

    public LoginUsecase() {
        userAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void signIn(String email, final String password) {
        Log.e(TAG, "signIn: email: " + email);
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
                viewListener.showLoginFailed();
            }

            @Override
            public void showInvalidPasswordError() {
                Log.e(TAG, "showInvalidPasswordError:");
                viewListener.hideProgress();
                viewListener.showInvalidPasswordLengthError();
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
                // user is not logged
            }
        });
    }


//    public void register(final String email, final String password) {
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.e(TAG, "onComplete: login");
//                        if (!task.isSuccessful()) {
//                            if (password.length() < 6) {
//                                signInListener.showInvalidPasswordError();
//                            } else {
//                                signInListener.onSignInError();
//                            }
//                        } else {
//                            signInListener.onSignInSuccessful(
//                                    firebaseAuth.getCurrentUser().getUid());
//                        }
//                    }
//                });
//
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "onFailure: login");
//                        signInListener.onSignInError();
//                    }
//                });
//    }

    public void validateUserData(String email, String password) {
        if (ValidatorUtils.validateEmail(email) && ValidatorUtils.validatePassword(password)) {
            signIn(email, password);
        } else if (!ValidatorUtils.validateEmail(email)) {
            viewListener.showInvalidEmail();
        } else if (!ValidatorUtils.validatePassword(password)) {
            viewListener.showInvalidPassword();

        }

    }


    public interface ViewListener {

        void hideProgress();

        void showProgress();

        void showInvalidPasswordLengthError();

        void showLoginFailed();

        void showInvalidEmail();

        void showInvalidPassword();

        void showLoginSuccess();
    }
}
