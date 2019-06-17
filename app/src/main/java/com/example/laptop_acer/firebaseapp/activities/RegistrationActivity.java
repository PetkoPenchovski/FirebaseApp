package com.example.laptop_acer.firebaseapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.model.User;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;
import com.example.laptop_acer.firebaseapp.usecases.RegistrationUsecase;

import java.util.List;

public class RegistrationActivity extends BaseActivity implements RegistrationUsecase.ViewListener {

    private ProgressBar progressBar;
    private ImageView imgViewReg;
    private EditText edtTxtName;
    private EditText edtTxtEmail;
    private EditText edtTxtPassword;
    private EditText edtTxtConfirmPassword;
    private EditText edtTxtPhoneNumber;
    private Button btnRegistration;
    private UserViewModel userViewModel;
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

        initiateUserViewModel();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOnClick();

            }
        });
    }


    private void initiateUserViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void setupOnClick() {
        String username = edtTxtName.getText().toString().trim();
        String email = edtTxtEmail.getText().toString().trim();
        String phoneNumber = edtTxtPhoneNumber.getText().toString().trim();
        String password = edtTxtPassword.getText().toString().trim();
        String confirmPassword = edtTxtConfirmPassword.getText().toString().trim();

        registrationUsecase.validateNewUserData(email, password, username, phoneNumber,
                confirmPassword);
    }

    private void addInRoom(UserDb userDb) {
        Log.e("PPP", "Add user in room");
        userViewModel.insert(userDb);
        userViewModel.getAllUsers().observe(this, new Observer<List<UserDb>>() {

            @Override
            public void onChanged(@Nullable final List<UserDb> userDb) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                for (int i = 0; i < userDb.size(); i++) {
                    System.out.println("Users in Room" + userDb);
                    System.out.println(i);
                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_registration;
    }

    private void openMainActivity() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
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
        Toast.makeText(this, (getString(R.string.invalid_email)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidRegPassword() {
        Toast.makeText(this, (getString(R.string.invalid_pass)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidRegUsername() {
        Toast.makeText(this, (getString(R.string.invalid_username)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidRegPhoneNumber() {
        Toast.makeText(this, (getString(R.string.invalid_phone_number)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainScreen(String username) {
        Toast.makeText(this, (getString(R.string.you_are_registered)),
                Toast.LENGTH_SHORT).show();
        openMainActivity();
    }

    @Override
    public void showPasswordMismatch() {
        Toast.makeText(this, (getString(R.string.mismatch_pass)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addUserToLocalDb(UserDb userDb) {
        addInRoom(userDb);
    }

    @Override
    public void onUserIdReceived(String id) {

    }
}



