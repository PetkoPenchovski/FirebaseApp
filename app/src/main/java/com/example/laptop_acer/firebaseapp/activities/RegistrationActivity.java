package com.example.laptop_acer.firebaseapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.usecases.RegistrationUsecase;

public class RegistrationActivity extends BaseActivity implements RegistrationUsecase.ViewListener {

    private ProgressBar progressBar;
    private ImageView imgViewReg;
    private EditText edtTxtName;
    private EditText edtTxtEmail;
    private EditText edtTxtPassword;
    private EditText edtTxtConfirmPassword;
    private EditText edtTxtPhoneNumber;
    private Button btnRegistration;

    private RegistrationUsecase registrationUsecase;

    @Override
    protected void onViewCreated() {
        registrationUsecase = new RegistrationUsecase();
        registrationUsecase.setViewListener(this);

        imgViewReg = findViewById(R.id.img_vw_reg);
        edtTxtName = findViewById(R.id.edt_txt_name);
        edtTxtEmail = findViewById(R.id.edt_txt_email);
        edtTxtPhoneNumber = findViewById(R.id.edt_txt_phone);
        edtTxtPassword = findViewById(R.id.edt_txt_password);
        edtTxtConfirmPassword = findViewById(R.id.edt_txt_confirm_password);
        btnRegistration = findViewById(R.id.btn_registration);
        progressBar = findViewById(R.id.progressbar);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtTxtName.getText().toString().trim();
                String email = edtTxtEmail.getText().toString().trim();
                String phoneNumber = edtTxtPhoneNumber.getText().toString().trim();
                String password = edtTxtPassword.getText().toString().trim();

                registrationUsecase.validateNewUserData(email, password, username, phoneNumber);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_registration;
    }

    @Override
    public void showSingUpSuccess() {
        String username = edtTxtName.getText().toString().trim();
        String userEmail = edtTxtEmail.getText().toString().trim();
        String userPhone = edtTxtPhoneNumber.getText().toString().trim();
        registrationUsecase.addUser(username, userPhone, userEmail);
    }

    @Override
    public void showSingUpFailed() {

    }

    @Override
    public void showSignUpFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInvalidRegEmail() {
        Toast.makeText(getApplicationContext(), (getString(R.string.invalid_email)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidRegPassword() {
        Toast.makeText(getApplicationContext(), (getString(R.string.invalid_pass)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidRegUsername() {
        Toast.makeText(getApplicationContext(), (getString(R.string.invalid_username)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidRegPhoneNumber() {
        Toast.makeText(getApplicationContext(), (getString(R.string.invalid_phone_number)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainScreen(String username) {
        Toast.makeText(getApplicationContext(), (getString(R.string.you_are_registered)),
                Toast.LENGTH_SHORT).show();
    }
}



