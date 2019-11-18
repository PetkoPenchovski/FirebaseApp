package com.example.laptop_acer.firebaseapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.activities.LoginActivity;
import com.example.laptop_acer.firebaseapp.activities.MainActivity;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.laptop_acer.firebaseapp.constants.Constants.TOOLBAR_TITLE_FRAGMENTS;

public class AccountFragment extends BaseFragment{

    private static UserViewModel userViewModel;
    private static UserDb userDb;
    private ProgressBar progressBarAccount;
    private static EditText edtTxtNameAccount;
    private static EditText edtTxtEmailAccount;
    private static EditText edtTxtPhoneNumberAccount;
    private static FirebaseDataRepository firebaseDataRepository;

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        firebaseDataRepository = new FirebaseDataRepository();
        bindElements();
        initiateUserViewModel();
        showUserInfo();
    }

    private void bindElements() {
        progressBarAccount = getLayoutView().findViewById(R.id.progressbar_account);
        edtTxtNameAccount = getLayoutView().findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = getLayoutView().findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = getLayoutView().findViewById(R.id.edt_txt_phone_account);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    public static void onEditUserInfo() {
        String username = edtTxtNameAccount.getText().toString().trim();
        String email = edtTxtEmailAccount.getText().toString().trim();
        String phone = edtTxtPhoneNumberAccount.getText().toString().trim();

        userDb = new UserDb
                (FirebaseAuthRepository.getInstance().getUserId(), username, email, phone);

        firebaseDataRepository.updateUser(username, email, phone);
        userViewModel.update(userDb);
    }

    private void initiateUserViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void showUserInfo() {
        userViewModel
                .getUserById(FirebaseAuthRepository.getInstance().getUserId()).observe(this,
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







