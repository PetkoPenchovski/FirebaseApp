package com.example.laptop_acer.firebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.auth.data.model.User;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageViewLogin;
    private EditText editTextPasswordLogin;
    private EditText editTextNicknameLogin;
    private Button buttonLogin;
    private Button buttonCreateRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewLogin = findViewById(R.id.img_vw_login);
        editTextPasswordLogin = findViewById(R.id.edt_txt_password_login);
        editTextNicknameLogin = findViewById(R.id.edt_txt_nickname_login);
        buttonLogin = findViewById(R.id.btn_login);
        buttonCreateRegistration = findViewById(R.id.btn_create_registration);

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
        String nickname = editTextNicknameLogin.getText().toString().trim();
        String password = editTextPasswordLogin.getText().toString().trim();

        if (nickname.isEmpty()) {
            editTextNicknameLogin.setError("Nickname is required or wrong");
            editTextNicknameLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPasswordLogin.setError("Password is required or wrong");
            editTextPasswordLogin.requestFocus();
            return;
        }

        if (password.length() <= 6) {
            editTextPasswordLogin.setError("Minimum 6 symbols");
            editTextPasswordLogin.requestFocus();
            return;
        }

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

}
