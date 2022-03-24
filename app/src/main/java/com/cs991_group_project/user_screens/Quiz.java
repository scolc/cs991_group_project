package com.cs991_group_project.user_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.JSONHandler;

import java.io.File;

public class Quiz extends AppCompatActivity {

    private File currentUsersFile, userDataFile, languageFile;
    private JSONHandler jh;
    private int index, qNum, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        index = getIntent().getIntExtra("index", 0);
        qNum = getIntent().getIntExtra("qNum", 0);
        score = getIntent().getIntExtra("score", 0);
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