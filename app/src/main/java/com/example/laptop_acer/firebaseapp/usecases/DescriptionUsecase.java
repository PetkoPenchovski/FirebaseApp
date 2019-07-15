package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.models.Task;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseImageRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseStorageSingleton;
import com.example.laptop_acer.firebaseapp.repositories.TaskDataRepository;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.storage.StorageReference;

public class DescriptionUsecase {

    private ViewListener viewListener;
    private StorageReference storageReference;
    private TaskDataRepository taskDataRepository;
    private FirebaseAuthRepository firebaseAuthRepository;
    private Task task;
    private String taskName;

    public DescriptionUsecase() {
        storageReference = FirebaseStorageSingleton.getInstance();
        firebaseAuthRepository = FirebaseAuthRepository.getInstance();
        taskDataRepository = new FirebaseDataRepository();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void showProgressBar() {
        viewListener.showProgress();
    }

    public void hideProgressBar() {
        viewListener.hideProgress();
    }

    public void registerTask(Task task) {
        showProgressBar();
        taskDataRepository.addTask(task, new DataListener<String>() {
            @Override
            public void onDataSuccess(String taskId) {
                hideProgressBar();
                viewListener.showTaskSavedSuccess(taskId);
            }

            @Override
            public void onDataError(String message) {
                hideProgressBar();
                viewListener.showTaskSavedFailure();
            }
        });
    }

    public void uploadImage(final String name, byte[] bytes) {
        showProgressBar();
        new FirebaseImageRepository().uploadImage(bytes, name, new DataListener<String>() {
            @Override
            public void onDataSuccess(String downloadLink) {
                hideProgressBar();
                if (!Strings.isEmptyOrWhitespace(downloadLink)) {
                    viewListener.uploadImageSuccess(downloadLink);
                } else {
                    viewListener.uploadImageSuccess(null);
                }
            }

            @Override
            public void onDataError(String message) {
                hideProgressBar();
                viewListener.uploadImageError(message);
            }
        });
    }

    public void validateNewTaskData(Task task) {
        if (ValidatorUtils.isValidateUrl(task.getUrlImage())
                && ValidatorUtils.isValidateText(task.getTaskName())
                && ValidatorUtils.isValidateText(task.getTaskDescription())
                && ValidatorUtils.isValidateText(task.getTaskLocation())
                && ValidatorUtils.isValidateText(task.getTime())
                ) {
            registerTask(task);
        } else if (!ValidatorUtils.isValidateText(task.getTaskName())) {
            viewListener.showInvalidTaskName();
        } else if (!ValidatorUtils.isValidateText(task.getTaskDescription())) {
            viewListener.showInvalidTaskDescription();
        } else if (!ValidatorUtils.isValidateText(task.getTaskLocation())) {
            viewListener.showInvalidTaskLocation();
        } else if (!ValidatorUtils.isValidateText(task.getTime())) {
            viewListener.showInvalidTime();
        }
    }

    public interface ViewListener {

        void showInvalidTaskName();

        void showInvalidTaskDescription();

        void showInvalidTaskLocation();

        void showInvalidTime();

        void showProgress();

        void hideProgress();

        void uploadImageSuccess(String downloadLink);

        void uploadImageError(String message);

        void showTaskSavedSuccess(String taskId);

        void showTaskSavedFailure();
    }
}

