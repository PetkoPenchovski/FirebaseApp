package com.example.laptop_acer.firebaseapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.usecases.LoginUsecase;

import static com.example.laptop_acer.firebaseapp.constants.Constants.TOOLBAR_TITLE;

public class LoginActivity extends BaseActivity implements LoginUsecase.ViewListener,
        View.OnClickListener {

    private ProgressBar progressBar;
    private ImageView imgViewLogin;
    private EditText edtTxtPasswordLogin;
    private EditText edtTxtEmailLogin;
    private Button btnLogin;
    private Button btnCreateRegistration;
    private LoginUsecase loginUsecase;
    private Toolbar toolbar;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated() {
        loginUsecase = new LoginUsecase();
        loginUsecase.setViewListener(this);
        loginUsecase.checkForLoggedUser();
        bindElements();
        setSupportActionBar(toolbar);
    }

    private void bindElements() {
        toolbar = findViewById(R.id.toolbar_login);
        toolbar.setTitle(TOOLBAR_TITLE);
        imgViewLogin = findViewById(R.id.img_vw_login);
        edtTxtPasswordLogin = findViewById(R.id.edt_txt_password_login);
        edtTxtEmailLogin = findViewById(R.id.edt_txt_email_login);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnCreateRegistration = findViewById(R.id.btn_create_registration);
        btnCreateRegistration.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar_login);
    }

    private void openRegistrationActivity() {
        showProgress();
        startActivity(RegistrationActivity.getIntent(LoginActivity.this));
    }

    private void btnLoginClicked() {
        showProgress();
        loginUsecase.validateUserData(edtTxtEmailLogin.getText().toString().trim(),
                edtTxtPasswordLogin.getText().toString().trim());
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
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
    public void showLoginSuccess() {
        startActivity(MainActivity.getIntent(LoginActivity.this));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                btnLoginClicked();
                break;
            case R.id.btn_create_registration:
                openRegistrationActivity();
                break;
        }
    }
}







