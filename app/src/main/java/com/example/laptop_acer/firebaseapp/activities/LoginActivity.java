package com.example.laptop_acer.firebaseapp.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.room_db.AppDatabase;
import com.example.laptop_acer.firebaseapp.room_db.UserDAO;
import com.example.laptop_acer.firebaseapp.usecases.LoginUsecase;
import com.example.laptop_acer.firebaseapp.usecases.UserAuthRepository;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends BaseActivity implements LoginUsecase.ViewListener {

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ImageView imgViewLogin;
    private EditText edtTxtPasswordLogin;
    private EditText edtTxtEmailLogin;
    private Button btnLogin;
    private Button btnCreateRegistration;

    private AppDatabase appDatabase;
    private UserDAO userDAO;

    private LoginUsecase loginUsecase;

    private UserAuthRepository.SignInListener signInListener;

    @Override
    protected void onViewCreated() {
        loginUsecase = new LoginUsecase();
        loginUsecase.setViewListener(this);
        loginUsecase.checkForLoggedUser();

        imgViewLogin = findViewById(R.id.img_vw_login);
        edtTxtPasswordLogin = findViewById(R.id.edt_txt_password_login);
        edtTxtEmailLogin = findViewById(R.id.edt_txt_email_login);
        btnLogin = findViewById(R.id.btn_login);
        btnCreateRegistration = findViewById(R.id.btn_create_registration);
        progressBar = findViewById(R.id.progressbar_login);

        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        userDAO = appDatabase.getUserDAO();

//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        btnCreateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                final String email = edtTxtEmailLogin.getText().toString().trim();
                final String password = edtTxtPasswordLogin.getText().toString().trim();
                loginUsecase.validateUserData(email, password);
                hideProgress();
            }
        });

    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

//    private void register(final String email, final String password) {
//        progressBar.setVisibility(View.VISIBLE);
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
//
//                        if (!task.isSuccessful()) {
//                            // there was an error
//                            if (password.length() < 6) {
//                                edtTxtPasswordLogin.setError(getString(R.string.minimum_password));
//                            } else {
//                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });
//    }



    //MVP
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }
//Opravi si progressbara 
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showInvalidPasswordLengthError() {

    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(getApplicationContext(), (getString(R.string.wrong_pass)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidEmail() {
        Toast.makeText(getApplicationContext(), (getString(R.string.wrong_email)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidPassword() {
        Toast.makeText(getApplicationContext(), (getString(R.string.minimum_password)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();

    }

}







