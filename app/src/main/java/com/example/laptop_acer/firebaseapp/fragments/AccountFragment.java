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

public class AccountFragment extends Fragment {

    private UserRoomDB userRoomDB;
    private ProgressBar progressBarAccount;
    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
    private EditText edtTxtPasswordAccount;
    private FloatingActionButton floatButton;
    private FloatingActionButton checkButton;
    private boolean isEdited;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        progressBarAccount = view.findViewById(R.id.progressbar_account);
        edtTxtNameAccount = view.findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        edtTxtPasswordAccount = view.findViewById(R.id.edt_txt_password_account);
        floatButton = view.findViewById(R.id.float_btn);
        checkButton = view.findViewById(R.id.check_btn);


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
            edtTxtNameAccount.setFocusableInTouchMode(true);
            edtTxtEmailAccount.setFocusableInTouchMode(true);
            edtTxtPhoneNumberAccount.setFocusableInTouchMode(true);
            edtTxtPasswordAccount.setFocusableInTouchMode(true);

        } else {
            edtTxtNameAccount.setFocusableInTouchMode(false);
            edtTxtEmailAccount.setFocusableInTouchMode(false);
            edtTxtPhoneNumberAccount.setFocusableInTouchMode(false);
            edtTxtPasswordAccount.setFocusableInTouchMode(false);


        }
        isEdited = !isEdited;
    }


}




