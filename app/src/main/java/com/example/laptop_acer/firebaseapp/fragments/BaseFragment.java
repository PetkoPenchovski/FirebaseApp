package com.example.laptop_acer.firebaseapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutRes(), container, false);
        onViewCreated();
        return view;
    }

    protected abstract int getLayoutRes();

    protected abstract void onViewCreated();

    protected View getLayoutView() {
        return view;
    }

}
