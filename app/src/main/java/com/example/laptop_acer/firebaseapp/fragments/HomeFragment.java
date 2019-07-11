package com.example.laptop_acer.firebaseapp.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.adapters.TasksAdapter;
import com.example.laptop_acer.firebaseapp.model.Task;
import com.example.laptop_acer.firebaseapp.usecases.HomeUsecase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class HomeFragment extends BaseFragment implements HomeUsecase.ViewListener, TasksAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private HomeUsecase usecase;
    private TasksAdapter adapter;

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        bindElements();
        loadTasks();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    private void bindElements() {
        usecase = new HomeUsecase();
        usecase.setViewListener(this);
        recyclerView = getLayoutView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TasksAdapter(getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadTasks() {
        usecase.getTasks();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        adapter.loadItems(tasks);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), getString(R.string.error_task),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_LONG).show();
    }
}