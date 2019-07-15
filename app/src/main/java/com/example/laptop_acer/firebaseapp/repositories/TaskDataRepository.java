package com.example.laptop_acer.firebaseapp.repositories;

import com.example.laptop_acer.firebaseapp.models.Task;
import com.example.laptop_acer.firebaseapp.usecases.DataListener;

import java.util.List;

public interface TaskDataRepository {

    void addTask(Task task, DataListener<String> listener);

    void getTasks(DataListener<List<Task>> listener);
}
