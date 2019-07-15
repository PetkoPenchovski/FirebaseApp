package com.example.laptop_acer.firebaseapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.fragments.AccountFragment;
import com.example.laptop_acer.firebaseapp.fragments.DescriptionFragment;
import com.example.laptop_acer.firebaseapp.fragments.HomeFragment;
import com.example.laptop_acer.firebaseapp.usecases.MainUsecase;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.laptop_acer.firebaseapp.constants.Constants.TOOLBAR_TITLE_FRAGMENTS;

public class MainActivity extends BaseActivity implements MainUsecase.ViewListener,
        BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private MainUsecase mainUsecase;
    private Toolbar toolbar;
    private ImageButton edtBtn;
    private ImageButton checkBtn;

    @Override
    protected void onViewCreated() {
        mainUsecase = new MainUsecase();
        mainUsecase.setViewListener(this);
        bindElements();
        openAccountFragment();
    }

    private void bindElements() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((TextView) toolbar.findViewById(R.id.title)).setText(TOOLBAR_TITLE_FRAGMENTS);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        edtBtn = findViewById(R.id.btn_edt);
        edtBtn.setOnClickListener(this);
        checkBtn = findViewById(R.id.btn_check);
        checkBtn.setOnClickListener(this);
    }

    private void openAccountFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AccountFragment()).commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                edtBtn.setVisibility(View.GONE);
                checkBtn.setVisibility(View.GONE);
                break;
            case R.id.nav_description:
                selectedFragment = new DescriptionFragment();
                edtBtn.setVisibility(View.GONE);
                checkBtn.setVisibility(View.GONE);
                break;
            case R.id.nav_account:
                selectedFragment = new AccountFragment();
                edtBtn.setVisibility(View.VISIBLE);
                checkBtn.setVisibility(View.VISIBLE);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSignOut)
            FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        Toast.makeText(this, getString(R.string.you_are_sign_out), Toast.LENGTH_SHORT)
                .show();
        return super.onOptionsItemSelected(item);
    }

    private void onEditButtonVisible() {
        checkBtn.setVisibility(View.GONE);
        edtBtn.setVisibility(View.VISIBLE);
    }

    private void onCheckButtonVisible() {
        edtBtn.setVisibility(View.GONE);
        checkBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edt:
                AccountFragment.onEditUserInfo();
                onCheckButtonVisible();
                break;
            case R.id.btn_check:
                onEditButtonVisible();
                AccountFragment.onEditUserInfo();
        }
    }
}

