package com.example.laptop_acer.firebaseapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.models.Task;
import com.google.android.gms.common.util.Strings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> tasks;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public TasksAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        tasks = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Task task = tasks.get(position);
        holder.taskName.setText("Name: " + task.getTaskName());
        holder.taskDescription.setText("Description: " + task.getTaskDescription());
        holder.taskLocation.setText("Location: " + task.getTaskLocation());
        holder.taskTime.setText("Time:" + task.getTime());

        if (!Strings.isEmptyOrWhitespace(task.getUrlImage())) {
            Picasso.get().load(task.getUrlImage()).into(holder.taskImg);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskDescription;
        TextView taskLocation;
        TextView taskTime;
        ImageView taskImg;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.txt_item_task);
            taskDescription = itemView.findViewById(R.id.txt_item_task_description);
            taskLocation = itemView.findViewById(R.id.txt_item_location);
            taskTime = itemView.findViewById(R.id.txt_item_time);
            taskImg = itemView.findViewById(R.id.img_vw_item);
            cardView = itemView.findViewById(R.id.card_view_item);
        }
    }

    public void loadItems(List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Task task);
    }
}
