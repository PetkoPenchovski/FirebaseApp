package com.example.laptop_acer.firebaseapp.activities;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.usecases.RegistrationUsecase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
//                String name = edtTxtName.getText().toString().trim();
//                String email = edtTxtEmail.getText().toString().trim();
//                String phone = edtTxtPhoneNumber.getText().toString().trim();
//                String password = edtTxtPassword.getText().toString().trim();
//                String confirmPassword = edtTxtConfirmPassword.getText().toString().trim();
//
//                if (name.isEmpty()) {
//                    edtTxtName.setError("Nickname is required");
//                    edtTxtName.requestFocus();
//                    return;
//                }
//                if (email.isEmpty()) {
//                    edtTxtEmail.setError("Email is required");
//                    edtTxtEmail.requestFocus();
//                    return;
//                }
//
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    edtTxtEmail.setError("Please enter a valid email");
//                    edtTxtEmail.requestFocus();
//                    return;
//                }
//
//                if (phone.isEmpty()) {
//                    edtTxtPhoneNumber.setError("Phone number is required");
//                    edtTxtPhoneNumber.requestFocus();
//                    return;
//                }
//
//                if (password.isEmpty()) {
//                    edtTxtPassword.setError("Password is required");
//                    edtTxtPassword.requestFocus();
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    edtTxtPassword.setError("Minimum 6 symbols");
//                    edtTxtPassword.requestFocus();
//                    return;
//                }
//
//                if (!confirmPassword.equals(password)) {
//                    edtTxtConfirmPassword.setError("The password not match");
//                    edtTxtConfirmPassword.requestFocus();
//                    return;
//                }
//
//                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)
//                        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
//                    register(name, email, phone, password, confirmPassword);
//                }
//                addUsers();
//
//            }
//
//            private void register(final String name, final String email, final String phone, final String password,
//                                  final String confirmPassword) {
//
//                progressBar.setVisibility(View.VISIBLE);
//                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
//                        if (!task.isSuccessful()) {
//                            checkEmailAlreadyExist();
//                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//                        } else {
//                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//                            Toast.makeText(getApplicationContext(), "Congratulations, you  have new registration!", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//            private void checkEmailAlreadyExist() {
//                firebaseAuth.fetchProvidersForEmail(edtTxtEmail.getText().toString())
//                        .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
//
//                                boolean check = !task.getResult().getProviders().isEmpty();
//
//                                if (!check) {
//                                    Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//
//            @Override
//            protected void onResume() {
//                super.onResume();
//                progressBar.setVisibility(View.GONE);
//            }
//
//            private void addUsers() {
//                String nameUser = edtTxtName.getText().toString().trim();
//                String emailUser = edtTxtEmail.getText().toString().trim();
//                String phoneUser = edtTxtPhoneNumber.getText().toString().trim();
//                String passUser = edtTxtPassword.getText().toString().trim();
//
//                if (!TextUtils.isEmpty(nameUser)) {
//
//                    String id = databaseReference.push().getKey();
//                    User user = new User(id, nameUser, emailUser, phoneUser, passUser);
//                    databaseReference.child(id).setValue(user);
//
//                }
//
//            }

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



