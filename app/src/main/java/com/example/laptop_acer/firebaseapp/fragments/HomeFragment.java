package com.example.laptop_acer.firebaseapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.model.Task;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeFragment extends BaseFragment {

    private static final String NAME_DB_TASK = "tasks";
    private static final String TAG = "HomeFragment";

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentTaskID;

    @Override
    protected void onFragmentViewCreated(View view, Bundle savedInstanceState) {
        bindElements();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    private void bindElements() {
        recyclerView = getLayoutView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseAuth = FirebaseAuth.getInstance();
        currentTaskID = firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(NAME_DB_TASK);
        loadTasks();
    }

    private void loadTasks() {
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Task>()
                .setQuery(databaseReference, Task.class)
                .build();

        FirebaseRecyclerAdapter<Task, TaskViewHolder> adapter =
                new FirebaseRecyclerAdapter<Task, TaskViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final TaskViewHolder holder, int position
                            , @NonNull Task model) {
                        String taskID = getRef(position).getKey();
                        databaseReference.child(taskID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String name = dataSnapshot.child("taskName")
                                        .getValue().toString();
                                String description = dataSnapshot.child("taskDescription")
                                        .getValue().toString();

                                holder.taskName.setText(name);
                                holder.taskDescription.setText(description);

                                if (dataSnapshot.hasChild("urlImage")) {
                                    String image = dataSnapshot.child("urlImage")
                                            .getValue().toString();
                                    Picasso.get().load(image).into(holder.taskImg);//no placeHolder
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e(TAG, "onCancelled: Getting tasks failed: "
                                        + databaseError.getMessage());
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        Log.e(TAG, "onCreateViewHolder: exec");
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.recycler_view_items, viewGroup, false);
                        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
                        return taskViewHolder;
                    }
                };
        Log.e(TAG, "loadTasks: start to setAdapter");
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskDescription;
        ImageView taskImg;

        private TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e(TAG, "TaskViewHolder: init");
            taskName = itemView.findViewById(R.id.txt_item_task);
            taskDescription = itemView.findViewById(R.id.txt_item_task_description);
            taskImg = itemView.findViewById(R.id.img_vw_item);
        }
    }
}