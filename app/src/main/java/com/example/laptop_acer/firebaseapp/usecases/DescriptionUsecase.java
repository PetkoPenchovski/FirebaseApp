package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.remote.FirebaseImageRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseStorageSingleton;
import com.google.firebase.storage.StorageReference;

public class DescriptionUsecase {

    private ViewListener viewListener;
    private StorageReference storageReference;

    public void setViewListener(ViewListener viewListener) {
        storageReference = FirebaseStorageSingleton.getInstance();
        this.viewListener = viewListener;
    }

    public void showProgressBar() {
        viewListener.showProgress();
    }

    public void hideProgressBar() {
        viewListener.hideProgress();
    }

    public void uploadImage(String name, byte[] bytes) {
        showProgressBar();
        new FirebaseImageRepository().uploadImage(bytes, name, new DataListener<Boolean>() {
            @Override
            public void onDataSuccess(Boolean data) {
                hideProgressBar();
                viewListener.uploadImageSuccess();
            }

            @Override
            public void onDataError(String message) {
                hideProgressBar();
                viewListener.uploadImageError(message);
            }
        });
    }


    public interface ViewListener {
        void showProgress();

        void hideProgress();

        void uploadImageSuccess();

        void uploadImageError(String message);
    }
}

