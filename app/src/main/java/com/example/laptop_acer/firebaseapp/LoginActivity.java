package com.example.laptop_acer.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ImageView imageViewLogin;
    private EditText inputTextPasswordLogin;
    private EditText inputTextEmailLogin;
    private Button buttonLogin;
    private Button buttonCreateRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewLogin = findViewById(R.id.img_vw_login);
        inputTextPasswordLogin = findViewById(R.id.edt_txt_password_login);
        inputTextEmailLogin = findViewById(R.id.edt_txt_email_login);
        buttonLogin = findViewById(R.id.btn_login);
        buttonCreateRegistration = findViewById(R.id.btn_create_registration);
        progressBar = findViewById(R.id.progressbar_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

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
        final String email = inputTextEmailLogin.getText().toString().trim();
        final String password = inputTextPasswordLogin.getText().toString().trim();

        if (email.isEmpty()) {
            inputTextEmailLogin.setError("Email is required or wrong");
            inputTextEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            inputTextPasswordLogin.setError("Password is required or wrong");
            inputTextPasswordLogin.requestFocus();
            return;
        }

        if (password.length() < 6) {
            inputTextPasswordLogin.setError("Minimum 6 symbols");
            inputTextPasswordLogin.requestFocus();
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
                                inputTextPasswordLogin.setError(getString(R.string.minimum_password));
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










