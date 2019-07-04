package com.example.laptop_acer.firebaseapp.remote;

import android.util.Log;

import com.example.laptop_acer.firebaseapp.model.Task;
import com.example.laptop_acer.firebaseapp.model.User;
import com.example.laptop_acer.firebaseapp.usecases.DataListener;
import com.example.laptop_acer.firebaseapp.usecases.TaskDataRepository;
import com.example.laptop_acer.firebaseapp.usecases.UserDataRepository;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataRepository implements UserDataRepository, TaskDataRepository {

    private static final String ERROR_TASK = "Error creating task node";
    private static final String FIREBASE_TABLE_USERS = "users";
    private static final String FIREBASE_TABLE_TASKS = "tasks";
    private static final String GET_URL_IMAGE = "urlImage";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_NAME = "userName";
    private static final String USER_PHONE = "userPhoneNumber";
    private static final String USER_ID = "userId";
    private static final String TASK_NAME = "taskName";
    private static final String TASK_DESCRIPTION = "taskDescription";
    private static final String TASK_LOCATION = "taskLocation";
    private static final String TASK_TIME = "taskTime";
    //Napravi si klas RoomDBConstants
    private DatabaseReference databaseUsers;
    private DatabaseReference databaseTasks;
    private UserDataListener userDataListener;



    public FirebaseDataRepository() {
        databaseUsers = FirebaseDatabase.getInstance().getReference(FIREBASE_TABLE_USERS);
        databaseTasks = FirebaseDatabase.getInstance().getReference(FIREBASE_TABLE_TASKS);
    }

    public void updateUser(String username, String email, String phone) {
        String userId = FirebaseAuthRepository.getInstance().getUserId();
        databaseUsers.child(userId).child(USER_EMAIL).setValue(email);
        databaseUsers.child(userId).child(USER_NAME).setValue(username);
        databaseUsers.child(userId).child(USER_PHONE).setValue(phone);

    }

    public void updateTask(Task task) {
        String idUser = FirebaseAuthRepository.getInstance().getUserId();
        databaseUsers.child(idUser).child(GET_URL_IMAGE).setValue(task.getUrlImage());
        databaseUsers.child(idUser).child(USER_ID).setValue(task.getUserId());
        databaseUsers.child(idUser).child(TASK_NAME).setValue(task.getTime());
        databaseUsers.child(idUser).child(TASK_DESCRIPTION).setValue(task.getTaskDescription());
        databaseUsers.child(idUser).child(TASK_LOCATION).setValue(task.getTaskLocation());
        databaseUsers.child(idUser).child(TASK_TIME).setValue(task.getTime());
    }

    @Override
    public void addUser(String id, String username, String phone, String email) {
        User user = new User(id, username, phone, email);
        databaseUsers.child(id).setValue(user);
        userDataListener.saveSuccess();
    }

    @Override
    public void addTask(Task task, DataListener<String> listener) {
        String taskId = databaseTasks.push().getKey();

        if(Strings.isEmptyOrWhitespace(taskId)) {
            listener.onDataError(ERROR_TASK);
        } else {
            databaseTasks.child(taskId).setValue(task);
            listener.onDataSuccess(taskId);
        }
    }

    @Override
    public void addUserDataListener(UserDataListener userDataListener) {
        this.userDataListener = userDataListener;
    }
}
