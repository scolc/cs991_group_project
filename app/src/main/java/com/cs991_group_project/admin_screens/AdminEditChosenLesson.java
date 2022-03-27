package com.cs991_group_project.admin_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;

import java.io.File;

public class AdminEditChosenLesson extends AppCompatActivity {

    private File languageFile;
    private JSONHandler jh;
    private String languageName;
    private Language language;
    private int lessonIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_chosen_lesson);

        languageName = getIntent().getStringExtra("languageName");
        lessonIndex = getIntent().getIntExtra("lessonIndex", 0);

        jh = new JSONHandler();
        languageFile = new File(getFilesDir(), languageName + ".json");

        language = jh.loadLanguage(languageFile);

        TextView tvLanguage = findViewById(R.id.tv_language);
        tvLanguage.setText(languageName);

        TextView tvLesson = findViewById(R.id.tv_lesson);
        tvLesson.setText(language.getLessons().get(lessonIndex).toString());
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, AdminEditLanguage.class);
        startActivity(intent);
    }

    public void onClickDelete(View view){

    }

    public void onClickSubmit(View view){

    }
}