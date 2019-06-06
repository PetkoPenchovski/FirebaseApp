package com.example.laptop_acer.firebaseapp.fragments;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.activities.MainActivity;
import com.example.laptop_acer.firebaseapp.room_db.UserRoomDB;

public class AccountFragment extends BaseFragment {

    private UserRoomDB userRoomDB;
    private ProgressBar progressBarAccount;
    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
    private EditText edtTxtPasswordAccount;
    private FloatingActionButton floatButton;
    private FloatingActionButton checkButton;
    private boolean isEdited;

    private static final String TAG = "AccountFragment";
    private MainActivity activity;


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

        userRoomDB = (UserRoomDB) getActivity().getIntent().getSerializableExtra("UserRoomDB");

        edtTxtNameAccount = view.findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        edtTxtPasswordAccount = view.findViewById(R.id.edt_txt_password_account);

        if (userRoomDB != null) {
            edtTxtNameAccount.setText(userRoomDB.getUserName().toString());
            edtTxtEmailAccount.setText(userRoomDB.getEmail().toString());
            edtTxtPhoneNumberAccount.setText(userRoomDB.getPhoneNumber().toString());
            edtTxtPasswordAccount.setText(userRoomDB.getPassword().toString());

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




