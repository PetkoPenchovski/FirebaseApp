package com.example.laptop_acer.firebaseapp.usecases;

import com.example.laptop_acer.firebaseapp.model.Task;

import java.util.List;

public interface TaskDataRepository {

    void addTask(Task task, DataListener<String> listener);

    void getTasks(DataListener<List<Task>> listener);
}
