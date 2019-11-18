package com.example.laptop_acer.firebaseapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getLayoutRes());
        onViewCreated();
    }

    protected BaseActivity getActivity() {
        return this;
    }

    protected abstract int getLayoutRes();

    protected abstract void onViewCreated();
}