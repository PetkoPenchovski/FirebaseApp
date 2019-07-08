package com.example.laptop_acer.firebaseapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.model.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> tasksList;
    private TaskViewHolder.OnItemClickListener listener;

    public TaskAdapter(ArrayList<Task> tasksList, TaskViewHolder.OnItemClickListener listener) {
        this.tasksList = tasksList;
        this.tasksList = new ArrayList<>();
        this.listener = listener;

    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_items, parent, false);
        return new TaskViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, final int position) {
        Picasso.get().load(tasksList.get(position).getUrlImage()).into(holder.taskImg);
        holder.taskName.setText(tasksList.get(position).getTaskName());
        holder.taskDescription.setText(tasksList.get(position).getTaskDescription());
        holder.taskImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(tasksList.get(position).getUrlImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void addItemsToTowns(ArrayList<Task> tasks) {
        this.tasksList = tasks;
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView taskName;
        private TextView taskDescription;
        private ImageView taskImg;

        public TaskViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            taskName = itemView.findViewById(R.id.txt_item_task);
            taskDescription = itemView.findViewById(R.id.txt_item_task_description);
            taskImg = itemView.findViewById(R.id.img_vw_item);
        }

        public interface OnItemClickListener {
            void onItemClick(String info);
        }
    }
}
