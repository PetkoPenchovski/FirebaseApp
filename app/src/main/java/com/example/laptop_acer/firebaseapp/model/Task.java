package com.example.laptop_acer.firebaseapp.model;

public class Task {

    private String userId;
    private String taskName;
    private String taskDescription;
    private String taskLocation;
    private String time;

    public Task(String userId, String taskName, String taskDescription,
                String taskLocation, String time) {
        this.userId = userId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskLocation = taskLocation;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
