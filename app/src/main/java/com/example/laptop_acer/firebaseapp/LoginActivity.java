package com.example.laptop_acer.firebaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageViewLogin;
    private EditText editTextPasswordLogin;
    private EditText EditTextNicknameLogin;
    private Button buttonLogin;
    private Button buttonCreateRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewLogin = findViewById(R.id.img_vw_login);
        editTextPasswordLogin = findViewById(R.id.edt_txt_password_login);
        EditTextNicknameLogin = findViewById(R.id.edt_txt_nickname_login);
        buttonLogin = findViewById(R.id.btn_login);
        buttonCreateRegistration = findViewById(R.id.btn_create_registration);

        buttonCreateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
