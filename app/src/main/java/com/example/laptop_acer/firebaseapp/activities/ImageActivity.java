package com.example.laptop_acer.firebaseapp.activities;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.models.Task;
import com.squareup.picasso.Picasso;

import static com.example.laptop_acer.firebaseapp.constants.Constants.EXTRA_TASK;

public class ImageActivity extends BaseActivity {

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
        name.setText(task.getTaskName());
        description.setText(task.getTaskDescription());
        location.setText(task.getTaskLocation());
        time.setText(task.getTime());
        Picasso.get().load(task.getUrlImage()).into(imgView);
        imgView.setImageURI(Uri.parse(task.getUrlImage()));
    }
}
