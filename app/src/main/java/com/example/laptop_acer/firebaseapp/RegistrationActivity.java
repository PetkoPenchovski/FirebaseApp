package com.example.laptop_acer.firebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
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

        progressDialog = new ProgressDialog(this);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editTextNickname.getText().toString();  //is not like the video- miss getEditText
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (!TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    progressDialog.setTitle("Loading...");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();
                    register(nickname, email, password, confirmPassword);
                }
            }

            private void register(final String nickname, final String email, final String password,
                                  final String confirmPassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().
                                    child("Users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("nickname", nickname);
                            userMap.put("email", email);
                            userMap.put("password", password);
                            userMap.put("confirm", confirmPassword);

                            databaseReference.setValue(userMap).addOnSuccessListener
                                    (new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

}



