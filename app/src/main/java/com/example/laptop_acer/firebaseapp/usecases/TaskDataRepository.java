package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.model.Task;

public interface TaskDataRepository {

    void addTask(Task task, DataListener<String> listener);

    interface TaskDataListener {

        void saveTaskSuccess(String taskId);
    }
}
