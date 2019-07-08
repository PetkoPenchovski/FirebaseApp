package com.example.laptop_acer.firebaseapp.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.adapter.TaskAdapter;
import com.example.laptop_acer.firebaseapp.model.Task;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements TaskAdapter.TaskViewHolder.OnItemClickListener{

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.home_fragment_recycler_view;
    }

    @Override
    protected void onViewCreated() {
        bindElements();
    }

    private void bindElements() {
        recyclerView = view.findViewById(R.id.recycler_view);

        ArrayList<Task> tasks = new ArrayList<>();//FillCarArrayList.getCars();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(tasks, this);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void onItemClick(String info) {

    }
}
