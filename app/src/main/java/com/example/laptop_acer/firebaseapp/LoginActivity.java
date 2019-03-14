package com.example.laptop_acer.firebaseapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.room_db.AppDatabase;
import com.example.laptop_acer.firebaseapp.room_db.UserDAO;
import com.example.laptop_acer.firebaseapp.room_db.UserRoomDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ImageView imgViewLogin;
    private EditText edtTxtPasswordLogin;
    private EditText edtTxtEmailLogin;
    private Button btnLogin;
    private Button btnCreateRegistration;

    private AppDatabase appDatabase;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        userDAO = appDatabase.getUserDAO();



        imgViewLogin = findViewById(R.id.img_vw_login);
        edtTxtPasswordLogin = findViewById(R.id.edt_txt_password_login);
        edtTxtEmailLogin = findViewById(R.id.edt_txt_email_login);
        btnLogin = findViewById(R.id.btn_login);
        btnCreateRegistration = findViewById(R.id.btn_create_registration);
        progressBar = findViewById(R.id.progressbar_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnCreateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginSuccess();
            }
        });
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void onLoginSuccess() {
        final String email = edtTxtEmailLogin.getText().toString().trim();
        final String password = edtTxtPasswordLogin.getText().toString().trim();

        if (email.isEmpty()) {
            edtTxtEmailLogin.setError("Email is required or wrong");
            edtTxtEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtTxtPasswordLogin.setError("Password is required or wrong");
            edtTxtPasswordLogin.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtTxtPasswordLogin.setError("Minimum 6 symbols");
            edtTxtPasswordLogin.requestFocus();
            return;
        }
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            register(email, password);

        }
    }

    private void register(final String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                edtTxtPasswordLogin.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}







