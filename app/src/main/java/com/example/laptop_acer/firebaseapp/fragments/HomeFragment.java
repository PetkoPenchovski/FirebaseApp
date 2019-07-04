package com.example.laptop_acer.firebaseapp.fragments;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.activities.MainActivity;

import static com.example.laptop_acer.firebaseapp.constants.Constants.TOOLBAR_TITLE_FRAGMENT;

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
        toolbar = view.findViewById(R.id.toolbar_home);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(TOOLBAR_TITLE_FRAGMENT);

    }
}
