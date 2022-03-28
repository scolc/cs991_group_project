package com.cs991_group_project.user_screens;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.User;

import java.io.File;


public class ProvideFeedback extends AppCompatActivity {
    private File currentUsersFile, userDataFile;
    private JSONHandler jh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_feedback);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");
        User user = jh.loadCurrentUserFromFile(currentUsersFile);
        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");
        CurrentUser thisUser = jh.loadUserDataFile(userDataFile);


    };
    public void onClickSubmit(View view) {
        finish();
    }

}
