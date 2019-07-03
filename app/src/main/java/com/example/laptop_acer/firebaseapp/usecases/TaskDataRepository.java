package com.example.laptop_acer.firebaseapp.usecases;

public interface TaskDataRepository {

    void addTask(String userId, String taskName, String taskDescription, String taskLocation,
                 String time);

    interface TaskDataListener {
        void saveTaskSuccess();
    }
}
