package com.cs991_group_project.user_screens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.User;

import java.io.File;
import java.util.Calendar;

public class UserHome extends AppCompatActivity {

    private File currentUsersFile, userDataFile;
    private JSONHandler jh;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");

        User user = jh.loadCurrentUserFromFile(currentUsersFile);

        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");

        if (!userDataFile.exists()) {
            CurrentUser currentUser = new CurrentUser();
            currentUser.setEmail(user.getEmail());
            currentUser.setUName(user.getUName());
            currentUser.setJoinDateFromDate(Calendar.getInstance());

            jh.saveUserDataFile(userDataFile, currentUser);
        }
    }

    public void onClickContinue(View view) {
        Intent intent = new Intent(this, Lessons.class);
        startActivity(intent);
    }

    public void onClickMenu(View view) {
    }
}