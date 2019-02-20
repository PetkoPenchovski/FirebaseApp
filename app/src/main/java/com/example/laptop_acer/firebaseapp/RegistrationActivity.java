package com.example.laptop_acer.firebaseapp;

import android.arch.persistence.room.Room;
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

import com.example.laptop_acer.firebaseapp.room_db.AppDatabase;
import com.example.laptop_acer.firebaseapp.room_db.UserRoomDB;
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
    private ImageView imageViewReg;
    private EditText inputTextName;
    private EditText inputTextEmail;
    private EditText inputTextPassword;
    private EditText inputTextConfirmPassword;
    private EditText inputPhoneNumber;
    private Button buttonRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        imageViewReg = findViewById(R.id.img_vw_reg);
        inputTextName = findViewById(R.id.edt_txt_name);
        inputTextEmail = findViewById(R.id.edt_txt_email);
        inputPhoneNumber = findViewById(R.id.edt_txt_phone);
        inputTextPassword = findViewById(R.id.edt_txt_password);
        inputTextConfirmPassword = findViewById(R.id.edt_txt_confirm_password);
        buttonRegistration = findViewById(R.id.btn_registration);
        progressBar = findViewById(R.id.progressbar);

        //Room
        final AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext()
                , AppDatabase.class, "production").allowMainThreadQueries()
                .build();
           //

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputTextName.getText().toString().trim();
                String email = inputTextEmail.getText().toString().trim();
                String phone = inputPhoneNumber.getText().toString().trim();
                String password = inputTextPassword.getText().toString().trim();
                String confirmPassword = inputTextConfirmPassword.getText().toString().trim();

                if (name.isEmpty()) {
                    inputTextName.setError("Nickname is required");
                    inputTextName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    inputTextEmail.setError("Email is required");
                    inputTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    inputTextEmail.setError("Please enter a valid email");
                    inputTextEmail.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    inputPhoneNumber.setError("Phone number is required");
                    inputPhoneNumber.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    inputTextPassword.setError("Password is required");
                    inputTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    inputTextPassword.setError("Minimum 6 symbols");
                    inputTextPassword.requestFocus();
                    return;
                }

                if (!confirmPassword.equals(password)) {
                    inputTextConfirmPassword.setError("The password not match");
                    inputTextConfirmPassword.requestFocus();
                    return;
                }

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)
                        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    register(name, email, phone, password, confirmPassword);
                }
                addUsers();
                //Room
                appDatabase.mUserDAO().insert(new UserRoomDB("Petko"
                        , "petkopenchovski@gmail.com", "+359895047511"
                        , "123456", "123456"));
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                //
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
                        } else {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            Toast.makeText(getApplicationContext(), "Congratulations, you  have new registration!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void checkEmailAlreadyExist() {
        firebaseAuth.fetchProvidersForEmail(inputTextEmail.getText().toString())
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
        String nameUser = inputTextName.getText().toString().trim();
        String phoneUser = inputPhoneNumber.getText().toString().trim();

        if (!TextUtils.isEmpty(nameUser)) {

            String id = databaseReference.push().getKey();
            User user = new User(id, nameUser, phoneUser);
            databaseReference.child(id).setValue(user);

        }
    }

}




