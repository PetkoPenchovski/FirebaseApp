package com.example.laptop_acer.firebaseapp.remote;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.laptop_acer.firebaseapp.usecases.UserAuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import static com.firebase.ui.auth.ui.email.CheckEmailFragment.TAG;

public class FirebaseAuthRepository implements UserAuthRepository {

    private static FirebaseAuthRepository instance;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private SignInListener signInListener;
    private SignUpListener signUpListener;
    private ImageView imgViewLogin;
    private EditText edtTxtPasswordLogin;
    private EditText edtTxtEmailLogin;


    @Override
    public void registerUser(String email, String password, String username, String phoneNumber) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                signUpListener.onSignUpError(e.getMessage());
                            } catch (Exception e) {
                                signUpListener.onSignUpError();
                            }
                        } else {
                            signUpListener.onSignUpSuccessful(firebaseAuth.getCurrentUser().getUid());
                        }
                    }
                });
    }

    @Override
    public void loginUserByEmail(String email, final String password) {
        Log.e("PPPPPP", "TAGgg");
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "onComplete: login");
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                signInListener.showInvalidPasswordError();
                            } else {
                                signInListener.onSignInError();
                            }
                        } else {
                            signInListener.onSignInSuccessful(firebaseAuth.getCurrentUser().getUid());

                        }
                    }
                });

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: login");
                        signInListener.onSignInError();
                    }
                });
    }

    @Override
    public void addSignInListener(SignInListener signInListener) {
        this.signInListener = signInListener;
        firebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    public void addSignUpListener(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
        firebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    public void removeSignInListener() {

    }

    @Override
    public void addCheckUserListener(CheckUserListener checkUserListener) {

    }

    public static FirebaseAuthRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthRepository();
        }
        return instance;
    }

    private FirebaseAuthRepository() {
        setupFireBaseAuthentication();
    }

    private void setupFireBaseAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();
        checkForSignedUser(signInListener);
    }

    public void checkForSignedUser(final SignInListener signInListener) {
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "User signed-in or registered and signed in automatically:" + user.getUid());
                } else if (signInListener != null) {
                    signInListener.onSignInSuccessful(firebaseAuth.getCurrentUser().getUid());
                }
            }
        };
    }

    @Override
    public void getCurrentUser(SignOutListener listener) {
        listener.onUserInstanceReceived(firebaseAuth);
    }

    @Override
    public void signOut() {

    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public void addResetPasswordListener(ResetPasswordListener resetPasswordListener) {

    }

    @Override
    public void checkForLoggedUser(CheckUserListener listener) {
        if (firebaseAuth.getCurrentUser() != null) {
            listener.userLoggedSuccessful();
        } else {
            listener.userLoggedError();
        }
    }

}


