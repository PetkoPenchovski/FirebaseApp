package com.example.laptop_acer.firebaseapp;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.laptop_acer.firebaseapp.room_db.UserDAO;
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
    private EditText edtTxtName;
    private EditText edtTxtEmail;
    private EditText edtTxtPassword;
    private EditText edtTxtConfirmPassword;
    private EditText edtTxtPhoneNumber;
    private Button buttonRegistration;
    private UserRoomDB userRoomDB;
    private UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        imageViewReg = findViewById(R.id.img_vw_reg);
        edtTxtName = findViewById(R.id.edt_txt_name);
        edtTxtEmail = findViewById(R.id.edt_txt_email);
        edtTxtPhoneNumber = findViewById(R.id.edt_txt_phone);
        edtTxtPassword = findViewById(R.id.edt_txt_password);
        edtTxtConfirmPassword = findViewById(R.id.edt_txt_confirm_password);
        buttonRegistration = findViewById(R.id.btn_registration);
        progressBar = findViewById(R.id.progressbar);

        userDAO = Room.databaseBuilder(this, AppDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtTxtName.getText().toString().trim();
                String email = edtTxtEmail.getText().toString().trim();
                String phone = edtTxtPhoneNumber.getText().toString().trim();
                String password = edtTxtPassword.getText().toString().trim();
                String confirmPassword = edtTxtConfirmPassword.getText().toString().trim();

                if (!isEmpty()) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UserRoomDB userRoomDB = new UserRoomDB(edtTxtName.getText().toString(),
                                    edtTxtEmail.getText().toString(),
                                    edtTxtPhoneNumber.getText().toString(),
                                    edtTxtPassword.getText().toString(),
                                    edtTxtConfirmPassword.getText().toString());
                            userDAO.insert(userRoomDB);
//                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        }
                    }, 1000);

                } else {
                    Toast.makeText(RegistrationActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
                register(name, email, phone, password, confirmPassword);
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

    private boolean isEmpty() {
        String name = edtTxtName.getText().toString().trim();
        String email = edtTxtEmail.getText().toString().trim();
        String phone = edtTxtPhoneNumber.getText().toString().trim();
        String password = edtTxtPassword.getText().toString().trim();
        String confirmPassword = edtTxtConfirmPassword.getText().toString().trim();


        if (TextUtils.isEmpty(edtTxtName.getText().toString()) ||
                TextUtils.isEmpty(edtTxtEmail.getText().toString()) ||
                TextUtils.isEmpty(edtTxtPhoneNumber.getText().toString()) ||
                TextUtils.isEmpty(edtTxtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtTxtConfirmPassword.getText().toString())) {

            if (name.isEmpty()) {
                edtTxtName.setError("Nickname is required");
                edtTxtName.requestFocus();
                return true;
            }
            if (email.isEmpty()) {
                edtTxtEmail.setError("Email is required");
                edtTxtEmail.requestFocus();
                return true;
            }

            if (email.isEmpty()) {
                edtTxtEmail.setError("Email is required");
                edtTxtEmail.requestFocus();
                return true;
            }


            if (!Patterns.EMAIL_ADDRESS.matcher(email).

                    matches()) {
                edtTxtEmail.setError("Please enter a valid email");
                edtTxtEmail.requestFocus();
                return true;
            }

            if (phone.isEmpty()) {
                edtTxtPhoneNumber.setError("Phone number is required");
                edtTxtPhoneNumber.requestFocus();
                return true;
            }

            if (password.isEmpty()) {
                edtTxtPassword.setError("Password is required");
                edtTxtPassword.requestFocus();
                return true;
            }

            if (password.length() < 6) {
                edtTxtPassword.setError("Minimum 6 symbols");
                edtTxtPassword.requestFocus();
                return true;
            }

            if (!confirmPassword.equals(password)) {
                edtTxtConfirmPassword.setError("The password not match");
                edtTxtConfirmPassword.requestFocus();
                return true;
            }

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)
                    && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {

            }
            return true;

        } else {
            return false;
        }

    }

}






