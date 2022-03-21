package com.cs991_group_project.user_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs991_group_project.R;

public class Lessons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, Quiz.class);
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