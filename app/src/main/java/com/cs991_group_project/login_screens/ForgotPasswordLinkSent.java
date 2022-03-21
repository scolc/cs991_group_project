package com.cs991_group_project.login_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;


import com.cs991_group_project.R;


public class ForgotPasswordLinkSent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_linksent);

    }


    // method to go back to the login screen
    public void onClickHome(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }




}
