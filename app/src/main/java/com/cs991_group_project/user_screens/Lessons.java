package com.cs991_group_project.user_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;
import com.cs991_group_project.objects.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Lessons extends AppCompatActivity {

    private File currentUsersFile, userDataFile, languageFile;
    private JSONHandler jh;
    private String languageName;
    private Language language;
    private int languageIndex, lessonIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        languageIndex = getIntent().getIntExtra("index", 0);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");
        User user = jh.loadCurrentUserFromFile(currentUsersFile);
        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");
        CurrentUser thisUser = jh.loadUserDataFile(userDataFile);
        languageName = thisUser.getLanguages().get(languageIndex).getLangName();
        languageFile = new File(getFilesDir(), languageName + ".json");

        language = jh.loadLanguage(languageFile);

        ArrayList<String> lessonList = new ArrayList<>();


        for (Lesson lesson : language.getLessons()) {
            String lessonString = language.getLangName() + " " + lesson.toString();
            Lesson userLessonData = thisUser.getLanguages().get(languageIndex).getLessons().get(language.getLessons().indexOf(lesson));
            if (userLessonData.isLessonComplete()) {
                lessonString += "Score: " + userLessonData.getLessonScore();
            }
            lessonList.add(lessonString);
        }

        ListView listView = findViewById(R.id.lv_lessons);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lessonList);

        listView.setAdapter(ad);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lessonIndex = i;
                Button button = findViewById(R.id.bt_start);
                button.setEnabled(true);
            }
        });
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, Quiz.class);
        intent.putExtra("languageIndex", languageIndex);
        intent.putExtra("lessonIndex", lessonIndex);
        intent.putExtra("qIndex", 0);
        intent.putExtra("score", 0);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickHome(View view) {
        Intent intent = new Intent(this, UserHome.class);
        startActivity(intent);
    }

    public void onClickMenu(View view) {
    }

}