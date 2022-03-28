package com.cs991_group_project.user_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;
import com.cs991_group_project.objects.User;

import java.io.File;

public class MyProfile extends AppCompatActivity {

    private File currentUsersFile, userDataFile;
    private JSONHandler jh;
    private CurrentUser thisUser;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");

        User user = jh.loadCurrentUserFromFile(currentUsersFile);

        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");

        thisUser = jh.loadUserDataFile(userDataFile);

        TextView tvUserName = findViewById(R.id.tv_input_name);
        tvUserName.setText(thisUser.getUName());

        TextView tvUserEmail = findViewById(R.id.tv_input_email);
        tvUserEmail.setText(thisUser.getEmail());

        TextView tvUserDateJoined = findViewById(R.id.tv_input_datejoined);
        tvUserDateJoined.setText(thisUser.getJoinDate());

        addRows();

    }

    public void addRows() {
        TableLayout tl_lessons = findViewById(R.id.tl_lessons);
        for (Language language : thisUser.getLanguages()){
            TableRow row = new TableRow(this);
            TextView column1 = new TextView(this);
            column1.setText(language.getLangName());
            column1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
            column1.setGravity(Gravity.CENTER);
            column1.setTextColor(getResources().getColor(R.color.black));

            int counter = 0;
            int score = 0;
            for (Lesson lesson : language.getLessons()){
                if (lesson.isLessonComplete()){
                    counter++;
                    score = score + lesson.getLessonScore();
                }
            }
            TextView column2 = new TextView(this);
            column2.setText(String.valueOf(counter));
            column2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 5));
            column2.setGravity(Gravity.CENTER);
            column2.setTextColor(getResources().getColor(R.color.black));


            TextView column3 = new TextView(this);
            column3.setText(String.valueOf(score));
            column3.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
            column3.setGravity(Gravity.CENTER);
            column3.setTextColor(getResources().getColor(R.color.black));


            row.addView(column1);
            row.addView(column2);
            row.addView(column3);

            tl_lessons.addView(row);
        }
    }

    public void onClickSubmit(View view) {
        finish();
    }
}