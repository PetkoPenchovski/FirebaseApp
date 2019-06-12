package com.example.laptop_acer.firebaseapp.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.fragments.AccountFragment;
import com.example.laptop_acer.firebaseapp.fragments.DescriptionFragment;
import com.example.laptop_acer.firebaseapp.fragments.HomeFragment;
import com.example.laptop_acer.firebaseapp.room_db.UserDB;
import com.example.laptop_acer.firebaseapp.usecases.MainUsecase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements MainUsecase.ViewListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private MainUsecase mainUsecase = new MainUsecase();

    private EditText edtTxtNameAccount;
    private EditText edtTxtEmailAccount;
    private EditText edtTxtPhoneNumberAccount;
    private EditText edtTxtPasswordAccount;
    private UserDB userDB;

    @Override
    protected void onViewCreated() {
        mainUsecase.setViewListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        toolbar = findViewById(R.id.toolbar);

        edtTxtNameAccount = findViewById(R.id.edt_txt_name_account);
        edtTxtEmailAccount = findViewById(R.id.edt_txt_email_account);
        edtTxtPhoneNumberAccount = findViewById(R.id.edt_txt_phone_account);
        edtTxtPasswordAccount = findViewById(R.id.edt_txt_password_account);

        userDB = (UserDB) getActivity().getIntent().getSerializableExtra("UserDB");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryDark)));
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_description:
                            selectedFragment = new DescriptionFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSignOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(this, "You are sign out!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

}