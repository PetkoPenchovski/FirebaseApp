package com.example.laptop_acer.firebaseapp.remote;

import com.example.laptop_acer.firebaseapp.model.Task;
import com.example.laptop_acer.firebaseapp.model.User;
import com.example.laptop_acer.firebaseapp.usecases.TaskDataRepository;
import com.example.laptop_acer.firebaseapp.usecases.UserDataRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataRepository implements UserDataRepository, TaskDataRepository {

    private DatabaseReference databaseUsers;
    private UserDataListener userDataListener;
    private TaskDataListener taskDataListener;
    //Napravi si klas RoomDBConstants
    private static final String FIREBASE_TABLE_USERS = "users";
    private static final String FIREBASE_TABLE_TASKS = "tasks";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_NAME = "userName";
    private static final String USER_PHONE = "userPhoneNumber";
    private static final String USER_ID = "userId";
    private static final String TASK_NAME = "taskName";
    private static final String TASK_DESCRIPTION = "taskDescription";
    private static final String TASK_LOCATION = "taskLocation";
    private static final String TASK_TIME = "taskTime";


    public FirebaseDataRepository() {
        databaseUsers = FirebaseDatabase.getInstance().getReference(FIREBASE_TABLE_USERS);
        databaseUsers = FirebaseDatabase.getInstance().getReference(FIREBASE_TABLE_TASKS);
    }

    public void updateUser(String username, String email, String phone) {
        String userId = FirebaseAuthRepository.getInstance().getUserId();
        databaseUsers.child(userId).child(USER_EMAIL).setValue(email);
        databaseUsers.child(userId).child(USER_NAME).setValue(username);
        databaseUsers.child(userId).child(USER_PHONE).setValue(phone);

    }

    public void updateTask(String userId, String taskName, String taskDescription,
                           String taskLocation, String time) {
        String idUser = FirebaseAuthRepository.getInstance().getUserId();
        databaseUsers.child(idUser).child(USER_ID).setValue(userId);
        databaseUsers.child(idUser).child(TASK_NAME).setValue(taskName);
        databaseUsers.child(idUser).child(TASK_DESCRIPTION).setValue(taskDescription);
        databaseUsers.child(idUser).child(TASK_LOCATION).setValue(taskLocation);
        databaseUsers.child(idUser).child(TASK_TIME).setValue(time);
    }

    @Override
    public void addUser(String id, String username, String phone, String email) {
        User user = new User(id, username, phone, email);
        databaseUsers.child(id).setValue(user);
        userDataListener.saveSuccess();
    }

    @Override
    public void addTask(String userId, String taskName, String taskDescription,
                        String taskLocation, String time) {
        Task task = new Task(userId, taskName, taskDescription, taskLocation, time);
        databaseUsers.child(userId).setValue(task);
        taskDataListener.saveTaskSuccess();
    }

    @Override
    public void addUserDataListener(UserDataListener userDataListener) {
        this.userDataListener = userDataListener;
    }
}
