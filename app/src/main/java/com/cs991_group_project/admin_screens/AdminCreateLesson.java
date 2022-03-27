package com.cs991_group_project.admin_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cs991_group_project.R;

public class AdminCreateLesson extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_lesson);

        name = getIntent().getStringExtra("name");

        TextView tvLanguage = findViewById(R.id.tv_language);
        tvLanguage.setText("Lesson: " + name);
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, AdminAddLanguage.class);
        startActivity(intent);
    }

    public void onClickSubmit(View view) {
    }
}