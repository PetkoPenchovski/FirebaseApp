package com.example.laptop_acer.firebaseapp.fragments;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.room_db.UserDB;

public class AccountFragment extends BaseFragment {

    private UserDB userDB;
    private ProgressBar progressBarAccount;
    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
    private EditText edtTxtPasswordAccount;
    private FloatingActionButton floatButton;
    private FloatingActionButton checkButton;
    private boolean isEdited;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void onViewCreated(){

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

        userDB = (UserDB) getActivity().getIntent().getSerializableExtra("UserDB");

        edtTxtNameAccount = view.findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        edtTxtPasswordAccount = view.findViewById(R.id.edt_txt_password_account);

        if (userDB != null) {
            edtTxtNameAccount.setText(userDB.getUserName().toString().trim());
            edtTxtEmailAccount.setText(userDB.getEmail().toString().trim());
            edtTxtPhoneNumberAccount.setText(userDB.getPhoneNumber().toString().trim());
            edtTxtPasswordAccount.setText(userDB.getPassword().toString().trim());

        }

        edtTxtNameAccount = view.findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        edtTxtPasswordAccount = view.findViewById(R.id.edt_txt_password_account);


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




