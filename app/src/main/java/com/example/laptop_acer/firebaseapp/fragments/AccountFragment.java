package com.example.laptop_acer.firebaseapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;

public class AccountFragment extends BaseFragment {

    private UserViewModel userViewModel;
    private UserDb userDb;
    private ProgressBar progressBarAccount;
    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
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
    protected void onViewCreated() {
        initiateUserViewModel();
        progressBarAccount = view.findViewById(R.id.progressbar_account);
        edtTxtNameAccount = view.findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        floatButton = view.findViewById(R.id.float_btn);
        checkButton = view.findViewById(R.id.check_btn);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPenCLicked();

            }
        });

        showUserInfo();
    }

    private void initiateUserViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void onPenCLicked() {
        if (isEdited) {
            edtTxtNameAccount.setFocusableInTouchMode(true);
            edtTxtEmailAccount.setFocusableInTouchMode(true);
            edtTxtPhoneNumberAccount.setFocusableInTouchMode(true);
        } else {
            edtTxtNameAccount.setFocusableInTouchMode(false);
            edtTxtEmailAccount.setFocusableInTouchMode(false);
            edtTxtPhoneNumberAccount.setFocusableInTouchMode(false);
        }
        isEdited = !isEdited;
    }

    private void showUserInfo() {
        Log.e("LLL", FirebaseAuthRepository.getInstance().getUserId());
        userViewModel.getUserById(FirebaseAuthRepository.getInstance().getUserId()).observe(this,
                new Observer<UserDb>() {
                    @Override
                    public void onChanged(@Nullable UserDb localUser) {
                        edtTxtEmailAccount.setText(localUser.getEmail());
                        edtTxtPhoneNumberAccount.setText(localUser.getPhoneNumber());
                        edtTxtNameAccount.setText(localUser.getUserName());
                    }
                });
    }

    private void updateUser() {
//        UserDb userDb = new UserDb(edtTxtNameAccount.getText().toString(),
//                edtTxtEmailAccount.getText().toString(),
//                edtTxtPhoneNumberAccount.getText().toString(),
    }
}







