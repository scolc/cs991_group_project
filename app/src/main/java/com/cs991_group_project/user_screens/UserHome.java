package com.cs991_group_project.user_screens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class UserHome extends AppCompatActivity {

    private File currentUsersFile, userDataFile;
    private JSONHandler jh;
    private int index;

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

        CurrentUser thisUser = jh.loadUserDataFile(userDataFile);

        ListView listView = findViewById(R.id.lv_languages);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, thisUser.getLanguages());

        listView.setAdapter(ad);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Button button = findViewById(R.id.bt_continue);
                button.setEnabled(true);
            }
        });
    }

    public void onClickContinue(View view) {
        Intent intent = new Intent(this, Lessons.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    public void onClickMenu(View view) {
    }
}