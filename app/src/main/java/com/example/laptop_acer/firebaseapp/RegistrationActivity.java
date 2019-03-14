package com.example.laptop_acer.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ImageView imgViewReg;
    private EditText edtTxtName;
    private EditText edtTxtEmail;
    private EditText edtTxtPassword;
    private EditText edtTxtConfirmPassword;
    private EditText edtTxtPhoneNumber;
    private Button btnRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

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
                String name = edtTxtName.getText().toString().trim();
                String email = edtTxtEmail.getText().toString().trim();
                String phone = edtTxtPhoneNumber.getText().toString().trim();
                String password = edtTxtPassword.getText().toString().trim();
                String confirmPassword = edtTxtConfirmPassword.getText().toString().trim();

                if (name.isEmpty()) {
                    edtTxtName.setError("Nickname is required");
                    edtTxtName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    edtTxtEmail.setError("Email is required");
                    edtTxtEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtTxtEmail.setError("Please enter a valid email");
                    edtTxtEmail.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    edtTxtPhoneNumber.setError("Phone number is required");
                    edtTxtPhoneNumber.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    edtTxtPassword.setError("Password is required");
                    edtTxtPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    edtTxtPassword.setError("Minimum 6 symbols");
                    edtTxtPassword.requestFocus();
                    return;
                }

                if (!confirmPassword.equals(password)) {
                    edtTxtConfirmPassword.setError("The password not match");
                    edtTxtConfirmPassword.requestFocus();
                    return;
                }

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)
                        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    register(name, email, phone, password, confirmPassword);
                }
                addUsers();

            }

            private void register(final String name, final String email, final String phone, final String password,
                                  final String confirmPassword) {

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            checkEmailAlreadyExist();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Congratulations, you  have new registration!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void checkEmailAlreadyExist() {
        firebaseAuth.fetchProvidersForEmail(edtTxtEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                        boolean check = !task.getResult().getProviders().isEmpty();

                        if (!check) {
                            Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void addUsers() {
        String nameUser = edtTxtName.getText().toString().trim();
        String emailUser = edtTxtEmail.getText().toString().trim();
        String phoneUser = edtTxtPhoneNumber.getText().toString().trim();
        String passUser = edtTxtPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(nameUser)) {

            String id = databaseReference.push().getKey();
            User user = new User(id, nameUser, emailUser, phoneUser, passUser);
            databaseReference.child(id).setValue(user);


        }

    }

}






