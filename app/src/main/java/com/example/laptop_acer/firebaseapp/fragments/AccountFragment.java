package com.example.laptop_acer.firebaseapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.room_db.UserRoomDB;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;

public class AccountFragment extends Fragment {

    private UserViewModel userViewModel;
    private UserRoomDB userRoomDB;
    private ProgressBar progressBarAccount;
    private EditText editTextNameAccount;
    private EditText editTextEmailAccount;
    private EditText editPhoneNumberAccount;
    private EditText editTextPasswordAccount;
    private FloatingActionButton floatButton;
    private FloatingActionButton checkButton;
    private boolean isEdited;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        progressBarAccount = view.findViewById(R.id.progressbar_account);
        editTextNameAccount = view.findViewById(R.id.edt_txt_name_account);
        editTextEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        editPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        editTextPasswordAccount = view.findViewById(R.id.edt_txt_password_account);
        floatButton = view.findViewById(R.id.float_btn);
        checkButton = view.findViewById(R.id.check_btn);

        showUserRoomDB();

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPenCLicked();
            }
        });


        return view;
    }


    private void onPenCLicked() {
        if (isEdited) {
            editTextNameAccount.setFocusableInTouchMode(true);
            editTextEmailAccount.setFocusableInTouchMode(true);
            editPhoneNumberAccount.setFocusableInTouchMode(true);
            editTextPasswordAccount.setFocusableInTouchMode(true);

        } else {
            editTextNameAccount.setFocusableInTouchMode(false);
            editTextEmailAccount.setFocusableInTouchMode(false);
            editPhoneNumberAccount.setFocusableInTouchMode(false);
            editTextPasswordAccount.setFocusableInTouchMode(false);


        }
        isEdited = !isEdited;
    }

    private void showUserRoomDB() {
        String strUserName = editTextNameAccount.getText().toString().trim();
        String strEmail = editTextEmailAccount.getText().toString().trim();
        String strPhoneUser = editPhoneNumberAccount.getText().toString().trim();
        String strPassword = editTextPasswordAccount.getText().toString().trim();
        UserRoomDB userRoomDB = new UserRoomDB(strUserName, strEmail, strPhoneUser, strPassword);
        userRoomDB.getUserName();
        userRoomDB.getEmail();
        userRoomDB.getPhoneNumber();
        userRoomDB.getPhoneNumber();
        userViewModel.insert(userRoomDB);
    }

}




