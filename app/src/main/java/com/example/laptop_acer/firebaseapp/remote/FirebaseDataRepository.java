package com.example.laptop_acer.firebaseapp.remote;

import android.support.annotation.NonNull;

import com.example.laptop_acer.firebaseapp.models.Task;
import com.example.laptop_acer.firebaseapp.models.User;
import com.example.laptop_acer.firebaseapp.usecases.DataListener;
import com.example.laptop_acer.firebaseapp.repositories.TaskDataRepository;
import com.example.laptop_acer.firebaseapp.repositories.UserDataRepository;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.laptop_acer.firebaseapp.constants.Constants.ERROR_TASK;
import static com.example.laptop_acer.firebaseapp.constants.Constants.FIREBASE_TABLE_TASKS;
import static com.example.laptop_acer.firebaseapp.constants.Constants.FIREBASE_TABLE_USERS;
import static com.example.laptop_acer.firebaseapp.constants.Constants.GET_URL_IMAGE;
import static com.example.laptop_acer.firebaseapp.constants.Constants.TASK_DESCRIPTION;
import static com.example.laptop_acer.firebaseapp.constants.Constants.TASK_LOCATION;
import static com.example.laptop_acer.firebaseapp.constants.Constants.TASK_NAME;
import static com.example.laptop_acer.firebaseapp.constants.Constants.TASK_TIME;
import static com.example.laptop_acer.firebaseapp.constants.Constants.USER_EMAIL;
import static com.example.laptop_acer.firebaseapp.constants.Constants.USER_ID;
import static com.example.laptop_acer.firebaseapp.constants.Constants.USER_NAME;
import static com.example.laptop_acer.firebaseapp.constants.Constants.USER_PHONE;

public class FirebaseDataRepository implements UserDataRepository, TaskDataRepository {


    private DatabaseReference databaseUsers;
    private DatabaseReference databaseTasks;


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

//    public void updateTask(Task task) {
//        String idUser = FirebaseAuthRepository.getInstance().getUserId();
//        databaseUsers.child(idUser).child(GET_URL_IMAGE).setValue(task.getUrlImage());
//        databaseUsers.child(idUser).child(USER_ID).setValue(task.getUserId());
//        databaseUsers.child(idUser).child(TASK_NAME).setValue(task.getTime());
//        databaseUsers.child(idUser).child(TASK_DESCRIPTION).setValue(task.getTaskDescription());
//        databaseUsers.child(idUser).child(TASK_LOCATION).setValue(task.getTaskLocation());
//        databaseUsers.child(idUser).child(TASK_TIME).setValue(task.getTime());
//    }

    @Override
    public void addUser(String id, String username, String phone, String email,
                        DataListener<Void> listener) {
        User user = new User(id, username, phone, email);
        databaseUsers.child(id).setValue(user);
        listener.onDataSuccess(null);
    }

    @Override
    public void addTask(Task task, DataListener<String> listener) {
        String taskId = databaseTasks.push().getKey();

        if (Strings.isEmptyOrWhitespace(taskId)) {
            listener.onDataError(ERROR_TASK);
        } else {
            databaseTasks.child(taskId).setValue(task);
            listener.onDataSuccess(taskId);
        }
    }

    @Override
    public void getTasks(final DataListener<List<Task>> listener) {
        databaseTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Task> tasks = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Task task;
                    task = ds.getValue(Task.class);
                    task.setTaskId(ds.getKey());
                    tasks.add(task);
                }

                listener.onDataSuccess(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
