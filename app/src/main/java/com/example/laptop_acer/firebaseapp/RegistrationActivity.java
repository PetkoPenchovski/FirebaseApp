package com.example.laptop_acer.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView imageViewReg;
    private EditText editTextNickname;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        imageViewReg = findViewById(R.id.img_vw_reg);
        editTextNickname = findViewById(R.id.edt_txt_nickname);
        editTextEmail = findViewById(R.id.edt_txt_email);
        editTextPassword = findViewById(R.id.edt_txt_password);
        editTextConfirmPassword = findViewById(R.id.edt_txt_confirm_password);
        buttonRegistration = findViewById(R.id.btn_registration);
    }
}
