package com.example.laptop_acer.firebaseapp.remote;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.usecases.DataListener;
import com.example.laptop_acer.firebaseapp.usecases.ImageStorageRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseImageRepository implements ImageStorageRepository{

    private StorageReference storageReference;

    public FirebaseImageRepository() {
        this.storageReference = FirebaseStorageSingleton.getInstance();
    }

    @Override
    public void uploadImage(byte[] bytes, String name, final DataListener<Boolean> listener) {
        StorageReference filepath = storageReference.child(name);
        filepath.putBytes(bytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    listener.onDataSuccess(true);
                } else {
                    listener.onDataError(Resources.getSystem().getString(R.string.error_upload));
                }
            }
        });
    }
}
