package com.example.laptop_acer.firebaseapp.usecases;

import android.util.Log;

import com.example.laptop_acer.firebaseapp.model.Task;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseImageRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseStorageSingleton;
import com.example.laptop_acer.firebaseapp.utils.ValidatorUtils;
import com.google.firebase.storage.StorageReference;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class DescriptionUsecase {

    private TaskAuthRepository taskAuthRepository;
    private ViewListener viewListener;
    private StorageReference storageReference;
    private TaskDataRepository taskDataRepository;
    private FirebaseAuthRepository firebaseAuthRepository;
    private Task task;
    private String taskName;

    public DescriptionUsecase() {
        storageReference = FirebaseStorageSingleton.getInstance();
        this.firebaseAuthRepository = FirebaseAuthRepository.getInstance();
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

    public void registerTask(String taskName, String taskDescription,
                             String taskLocation, String time) {
        viewListener.showProgress();
        taskAuthRepository.addTaskSignUpListener(getSignTaskListener());
        taskAuthRepository.registerTask(firebaseAuthRepository.getUserId(), taskName, taskDescription, taskLocation, time);
        buildTask(firebaseAuthRepository.getUserId(), taskName, taskDescription, taskLocation, time);
        this.taskName = taskName;
    }

    private void buildTask(String userId, String taskName, String taskDescription,
                           String taskLocation, String time) {
        task.setUserId(userId);
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        task.setTaskLocation(taskLocation);
        task.setTime(time);
    }

    public void uploadImage(final String name, byte[] bytes) {
        showProgressBar();
        new FirebaseImageRepository().uploadImage(bytes, name, new DataListener<Boolean>() {
            @Override
            public void onDataSuccess(Boolean data) {
                hideProgressBar();
                getImagePath(name);
                viewListener.uploadImageSuccess();
            }

            @Override
            public void onDataError(String message) {
                hideProgressBar();
                viewListener.uploadImageError(message);
            }
        });
    }

    private void getImagePath(String name) {
        new FirebaseImageRepository().getImagePath(name, new ImageStorageRepository.ImageListener() {
            @Override
            public void onImageNameReceived(String name) {

            }
        });

    }

    private TaskAuthRepository.TaskListener getSignTaskListener() {
        return new TaskAuthRepository.TaskListener() {
            @Override
            public void onSignUpSuccessful(String userId) {

                Log.e(TAG, "onSignUpSuccessful:");
                viewListener.hideProgress();
                task.setUserId(userId);
                addTask(task);
            }

            @Override
            public void onSignUpError(String errorMessage) {
                Log.e(TAG, "onSignUpError: ");
                viewListener.hideProgress();
                viewListener.showTaskSignUpFailed(errorMessage);
            }

            @Override
            public void onSignUpError() {
                Log.e(TAG, "onSignUpError: ");
                viewListener.hideProgress();
                viewListener.showTaskSingUpFailed();
            }
        };
    }

    public void addTask(Task task) {
        taskDataRepository.addTask(task.getUserId(), task.getTaskName(), task.getTaskDescription(),
                task.getTaskLocation(), task.getTime());
    }

    public void validateNewTaskData(String taskName, String taskDescription,
                                    String taskLocation, String time) {
        if (ValidatorUtils.validatePassword(taskName)
                && ValidatorUtils.validateName(taskDescription)
                && ValidatorUtils.validatePhone(taskLocation)
                && ValidatorUtils.validatePhone(time)) {
            registerTask(taskName, taskDescription, taskLocation, time);
        } else if (!ValidatorUtils.validateText(taskName)) {
            viewListener.showInvalidTaskName();
        } else if (!ValidatorUtils.validateText(taskDescription)) {
            viewListener.showInvalidTaskDescription();
        } else if (!ValidatorUtils.validateText(taskLocation)) {
            viewListener.showInvalidTaskLocation();
        } else if (!ValidatorUtils.validateText(time)) {
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

        void uploadImageSuccess();

        void uploadImageError(String message);

        void downloadImageSuccess();

        void downloadImageError(String message);

        void showTaskSignUpFailed(String errorMessage);

        void showTaskSingUpFailed();
    }
}

