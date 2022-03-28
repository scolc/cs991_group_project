package com.cs991_group_project.user_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs991_group_project.R;
import com.cs991_group_project.login_screens.Login;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickLogout(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void onClickSetExp(View view) {
        Intent intent = new Intent(this, SetExperienceLevel.class);
        startActivity(intent);
    }


    public void onClickDict(View view) {
        Intent intent = new Intent(this, Dictionary.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View view) {
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void onClickProvideFeedback(View view) {
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }
}