package com.example.laptop_acer.firebaseapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;

public class AccountFragment extends BaseFragment implements View.OnClickListener {

    private UserViewModel userViewModel;
    private UserDb userDb;
    private ProgressBar progressBarAccount;
    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
    private FloatingActionButton floatButton;
    private FloatingActionButton checkButton;
    private FirebaseDataRepository firebaseDataRepository;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void onViewCreated() {
        firebaseDataRepository = new FirebaseDataRepository();
        bindElements();
        initiateUserViewModel();
        showUserInfo();
    }

    private void bindElements() {
        progressBarAccount = view.findViewById(R.id.progressbar_account);
        edtTxtNameAccount = view.findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = view.findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = view.findViewById(R.id.edt_txt_phone_account);
        floatButton = view.findViewById(R.id.float_btn);
        floatButton.setOnClickListener(this);
        checkButton = view.findViewById(R.id.check_btn);
        checkButton.setOnClickListener(this);
    }

    public void onFloatingBtnClicked() {
        String username = edtTxtNameAccount.getText().toString().trim();
        String email = edtTxtEmailAccount.getText().toString().trim();
        String phone = edtTxtPhoneNumberAccount.getText().toString().trim();

        userDb = new UserDb(FirebaseAuthRepository.getInstance().getUserId(), username, email, phone);

        firebaseDataRepository.updateUser(username, email, phone);
        userViewModel.update(userDb);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_btn:
                onFloatingBtnClicked();
                break;
        }
    }

    private void initiateUserViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void showUserInfo() {
        userViewModel.getUserById(FirebaseAuthRepository.getInstance().getUserId()).observe(this,
                new Observer<UserDb>() {
                    @Override
                    public void onChanged(@Nullable UserDb localUser) {
                        if (localUser != null) {
                            edtTxtEmailAccount.setText(localUser.getEmail());
                            edtTxtPhoneNumberAccount.setText(localUser.getPhoneNumber());
                            edtTxtNameAccount.setText(localUser.getUserName());
                        }
                    }
                });
    }

}







