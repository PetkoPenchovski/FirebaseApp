package com.example.laptop_acer.firebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageView imageViewLogin;
    private ProgressDialog progressDialog;
    private EditText editTextPasswordLogin;
    private EditText editTextEmailLogin;
    private Button buttonLogin;
    private Button buttonCreateRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewLogin = findViewById(R.id.img_vw_login);
        editTextPasswordLogin = findViewById(R.id.edt_txt_password_login);
        editTextEmailLogin = findViewById(R.id.edt_txt_email_login);
        buttonLogin = findViewById(R.id.btn_login);
        buttonCreateRegistration = findViewById(R.id.btn_create_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        buttonCreateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
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
        final String email = editTextEmailLogin.getText().toString().trim();
        final String password = editTextPasswordLogin.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmailLogin.setError("Email is required or wrong");
            editTextEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPasswordLogin.setError("Password is required or wrong");
            editTextPasswordLogin.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPasswordLogin.setError("Minimum 6 symbols");
            editTextPasswordLogin.requestFocus();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                editTextPasswordLogin.setError(getString(R.string.minimum_password));
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
}









