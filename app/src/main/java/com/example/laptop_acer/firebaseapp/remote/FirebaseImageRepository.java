package com.example.laptop_acer.firebaseapp.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.laptop_acer.firebaseapp.usecases.DataListener;
import com.example.laptop_acer.firebaseapp.repositories.ImageStorageRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseImageRepository implements ImageStorageRepository {

    private static final String UPLOAD_IMAGE_ERROR = "Not Image Found";
    private StorageReference storageReference;

    public FirebaseImageRepository() {
        this.storageReference = FirebaseStorageSingleton.getInstance();
    }

    @Override
    public void uploadImage(byte[] bytes, String name,
                            final DataListener<String> listener) {
        final StorageReference filepath = storageReference.child(name);
        filepath.putBytes(bytes).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    listener.onDataSuccess(downloadUri.toString());
                } else {
                    listener.onDataError(UPLOAD_IMAGE_ERROR);
                }
            }
        });
    }
}
