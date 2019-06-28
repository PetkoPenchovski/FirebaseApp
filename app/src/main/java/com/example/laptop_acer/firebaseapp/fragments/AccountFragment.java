package com.example.laptop_acer.firebaseapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.activities.LoginActivity;
import com.example.laptop_acer.firebaseapp.activities.MainActivity;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.room_db.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.laptop_acer.firebaseapp.constants.Constants.TOOLBAR_TITLE_FRAGMENT;

public class AccountFragment extends BaseFragment implements View.OnClickListener {

    private UserViewModel userViewModel;
    private UserDb userDb;
    private ProgressBar progressBarAccount;
    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
    private FirebaseDataRepository firebaseDataRepository;
    private Toolbar toolbar;
    private ImageButton edtBtn;
    private ImageButton checkBtn;
    private boolean isCheck = false;


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
        edtBtn = view.findViewById(R.id.btn_edt);
        edtBtn.setOnClickListener(this);
        checkBtn = view.findViewById(R.id.btn_check);
        checkBtn.setOnClickListener(this);
        toolbar = view.findViewById(R.id.toolbar_account);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(TOOLBAR_TITLE_FRAGMENT);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSignOut) {
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Toast.makeText(getActivity(), getString(R.string.you_are_sign_out), Toast.LENGTH_SHORT)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onPenBtnClicked() {
        String username = edtTxtNameAccount.getText().toString().trim();
        String email = edtTxtEmailAccount.getText().toString().trim();
        String phone = edtTxtPhoneNumberAccount.getText().toString().trim();

        edtTxtNameAccount.setEnabled(false);

        userDb = new UserDb
                (FirebaseAuthRepository.getInstance().getUserId(), username, email, phone);

        firebaseDataRepository.updateUser(username, email, phone);
        userViewModel.update(userDb);

    }

    private void onCheckBtnClicked(String username, String email, String phone) {
        firebaseDataRepository.updateUser(username, email, phone);
        userViewModel.update(userDb);

    }

    private void onEdit() {
        checkBtn.setVisibility(View.GONE);
        edtBtn.setVisibility(View.VISIBLE);
    }

    private void onCheck() {
        edtBtn.setVisibility(View.GONE);
        checkBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edt:
                onPenBtnClicked();
                onEdit();
                break;
            case R.id.btn_check:
//                onCheckBtnClicked();
                onCheck();
        }
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







