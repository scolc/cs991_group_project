package com.cs991_group_project.admin_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs991_group_project.R;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void onClickAddLesson(View view) {
        Intent  intent = new Intent(this, AdminAddLanguage.class);
        startActivity(intent);
    }
}