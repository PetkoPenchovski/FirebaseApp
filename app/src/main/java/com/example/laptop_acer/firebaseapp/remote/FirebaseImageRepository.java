package com.example.laptop_acer.firebaseapp.remote;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.usecases.DataListener;
import com.example.laptop_acer.firebaseapp.usecases.ImageStorageRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseImageRepository implements ImageStorageRepository {

    private StorageReference storageReference;

    public FirebaseImageRepository() {
        this.storageReference = FirebaseStorageSingleton.getInstance();
    }

    @Override
    public void uploadImage(byte[] bytes, String name,
                            final DataListener<Boolean> listener) {
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

    @Override
    public void getImagePath(String name, ImageListener listener) {
//        try {
//            File localFile = File.createTempFile("images", "jpg");
//            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        this.storageReference.child("images/" + name + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("PPPPP", String.valueOf(uri));
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }


}
