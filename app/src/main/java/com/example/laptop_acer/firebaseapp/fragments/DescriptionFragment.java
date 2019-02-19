package com.example.laptop_acer.firebaseapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop_acer.firebaseapp.R;

public class DescriptionFragment extends Fragment {

    private EditText inputTask;
    private EditText inputTaskDescription;
    private Button buttonCamera;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        inputTask = view.findViewById(R.id.edt_txt_task);
        inputTaskDescription = view.findViewById(R.id.edt_txt_task_description);
        buttonCamera = view.findViewById(R.id.btn_camera);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }
}
