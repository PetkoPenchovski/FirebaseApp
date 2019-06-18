package com.example.laptop_acer.firebaseapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.fragments.AccountFragment;
import com.example.laptop_acer.firebaseapp.fragments.DescriptionFragment;
import com.example.laptop_acer.firebaseapp.fragments.HomeFragment;
import com.example.laptop_acer.firebaseapp.room_db.UserDb;
import com.example.laptop_acer.firebaseapp.usecases.MainUsecase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements MainUsecase.ViewListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private MainUsecase mainUsecase = new MainUsecase();
    private UserDb userDb;

    @Override
    protected void onViewCreated() {
        mainUsecase.setViewListener(this);
        mainUsecase = new MainUsecase();
        bindElements();
        setupRoomDb();
        openAccountFragment();
    }

    private void bindElements() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void setupRoomDb() {
        userDb = (UserDb) getActivity().getIntent().getSerializableExtra("UserDb");
    }

    private void openAccountFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AccountFragment()).commit();
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
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

        return false;

    }
}

