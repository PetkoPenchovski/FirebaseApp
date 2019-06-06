package com.example.laptop_acer.firebaseapp.fragments;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop_acer.firebaseapp.R;

public class DescriptionFragment extends BaseFragment {

    private EditText inputTask;
    private EditText inputTaskDescription;
    private Button btnCamera;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_description;
    }

    @Override
    protected void onViewCreated() {
        inputTask = view.findViewById(R.id.edt_txt_task);
        inputTaskDescription = view.findViewById(R.id.edt_txt_task_description);
        btnCamera = view.findViewById(R.id.btn_camera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}
