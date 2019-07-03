package com.example.laptop_acer.firebaseapp.usecases;

public interface TaskAuthRepository {

    void registerTask(String userId, String taskName, String taskDescription,
                      String taskLocation, String time);

    void addTaskSignUpListener(TaskListener signUpListener);

    interface TaskListener {
        void onSignUpSuccessful(String userId);

        void onSignUpError(String errorMessage);

        void onSignUpError();
    }
}
