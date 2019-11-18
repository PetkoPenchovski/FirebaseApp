package com.example.laptop_acer.firebaseapp.remote;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.laptop_acer.firebaseapp.constants.Constants.IMAGE_FOLDER;
import static com.example.laptop_acer.firebaseapp.constants.Constants.STORAGE_URL;

public class FirebaseStorageSingleton {

    private static StorageReference instance;

    private FirebaseStorageSingleton() {
    }

    public static StorageReference getInstance() {
        if (instance == null) {
            instance = FirebaseStorage.getInstance()
                    .getReferenceFromUrl(STORAGE_URL)
                    .child(IMAGE_FOLDER);
        }
        return instance;
    }
}

