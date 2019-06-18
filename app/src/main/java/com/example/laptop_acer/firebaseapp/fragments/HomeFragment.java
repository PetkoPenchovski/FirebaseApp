package com.example.laptop_acer.firebaseapp.fragments;

import android.support.v7.widget.Toolbar;

import com.example.laptop_acer.firebaseapp.R;

public class HomeFragment extends BaseFragment {

  private Toolbar toolbar;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated() {
        bindElements();
    }

    private void bindElements() {
        toolbar = view.findViewById(R.id.toolbar_home_fragment);
    }
}
