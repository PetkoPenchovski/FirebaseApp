package com.example.laptop_acer.firebaseapp.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.room_db.UserRoomDBDAO;
import com.example.laptop_acer.firebaseapp.room_db.UserRoomDatabase;
import com.example.laptop_acer.firebaseapp.usecases.LoginUsecase;
import com.example.laptop_acer.firebaseapp.usecases.UserAuthRepository;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends BaseActivity implements LoginUsecase.ViewListener {

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ImageView imgViewLogin;
    private EditText edtTxtPasswordLogin;
    private EditText edtTxtEmailLogin;
    private Button btnLogin;
    private Button btnCreateRegistration;

    private UserRoomDatabase userRoomDatabase;
    private UserRoomDBDAO userRoomDBDAO;

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

        userRoomDatabase = Room.databaseBuilder(this, UserRoomDatabase.class, "user_database")
                .allowMainThreadQueries()
                .build();

        userRoomDBDAO = userRoomDatabase.userRoomDBDAO();

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
    public void showLoginFailed() {
        Toast.makeText(this, (getString(R.string.wrong_pass)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidEmail() {
        Toast.makeText(this, (getString(R.string.wrong_email)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidPassword() {
        Toast.makeText(this, (getString(R.string.invalid_pass)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();

    }

}







