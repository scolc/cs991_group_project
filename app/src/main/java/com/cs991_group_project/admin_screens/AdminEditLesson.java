package com.cs991_group_project.admin_screens;

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
import com.cs991_group_project.user_screens.Quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminEditLesson extends AppCompatActivity {

    private File languageFile;
    private JSONHandler jh;
    private String languageName;
    private Language language;
    private int lessonIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_lesson);

        languageName = getIntent().getStringExtra("name");

        jh = new JSONHandler();
        languageFile = new File(getFilesDir(), languageName + ".json");

        if (!languageFile.exists()) {
            setupLanguageFile();
        }

        language = jh.loadLanguage(languageFile);

        ArrayList<String> lessonList = new ArrayList<>();

        for (Lesson lesson : language.getLessons()) {
            lessonList.add(lesson.toString());
        }

        ListView listView = findViewById(R.id.lv_lessons);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lessonList);

        listView.setAdapter(ad);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lessonIndex = i;
                Button button = findViewById(R.id.bt_continue);
                button.setEnabled(true);
            }
        });
    }

    public void setupLanguageFile(){
        InputStream is;
        try {
            is = getAssets().open(languageName + ".json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            jh.setupFileFromAssets(languageFile, line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickContinue(View view) {
        Intent intent = new Intent(this, AdminEditChosenLesson.class);
        intent.putExtra("languageName", languageName);
        intent.putExtra("lessonIndex", lessonIndex);
        startActivity(intent);
    }
}