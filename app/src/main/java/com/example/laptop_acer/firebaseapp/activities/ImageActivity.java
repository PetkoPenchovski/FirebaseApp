package com.example.laptop_acer.firebaseapp.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.models.Task;
import com.squareup.picasso.Picasso;

public class ImageActivity extends BaseActivity {
    private static final String EXTRA_TASK = "Image";
    private ImageView imgView;
    private TextView name;
    private TextView description;
    private TextView location;
    private TextView time;
    private Task task;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_image;
    }

    @Override
    protected void onViewCreated() {
        getExtra();
        bindElements();
        setData();
    }

    public static Intent getIntent(Context context, Task task) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(EXTRA_TASK, task);
        return intent;
    }

    private void bindElements() {
        imgView = findViewById(R.id.image);
        name = findViewById(R.id.txt_name);
        description = findViewById(R.id.txt_description);
        location = findViewById(R.id.txt_location);
        time = findViewById(R.id.txt_time);
    }

    private void getExtra() {
        task = (Task) getIntent().getSerializableExtra(EXTRA_TASK);
    }

    private void setData() {
        name.setText("Name: " + task.getTaskName());
        description.setText("Description: " + task.getTaskDescription());
        location.setText("Location: " + task.getTaskLocation());
        time.setText("Time:" + task.getTime());
        Picasso.get().load(task.getUrlImage()).into(imgView);
    }
}
