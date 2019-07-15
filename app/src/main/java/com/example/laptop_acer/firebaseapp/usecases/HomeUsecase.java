package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.models.Task;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.repositories.TaskDataRepository;

import java.util.List;

public class HomeUsecase {

    private TaskDataRepository taskDataRepository;
    private ViewListener viewListener;
    private List<Task> tasks;

    public HomeUsecase() {
        taskDataRepository = new FirebaseDataRepository();
    }

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void getTasks() {
        taskDataRepository.getTasks(new DataListener<List<Task>>() {
            @Override
            public void onDataSuccess(List<Task> data) {
                tasks = data;
                viewListener.showTasks(tasks);
            }

            @Override
            public void onDataError(String message) {
                viewListener.showError(message);
            }
        });
    }

    public interface ViewListener {
        void showTasks(List<Task> tasks);

        void showError(String message);
    }
}
