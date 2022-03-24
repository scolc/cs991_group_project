package com.cs991_group_project.login_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.admin_screens.AdminHome;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.StoredUserList;
import com.cs991_group_project.objects.User;
import com.cs991_group_project.user_screens.UserHome;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Login extends AppCompatActivity {

    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private File usersFile;
    private JSONHandler jh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
        jh = new JSONHandler();

        usersFile = new File(getFilesDir(), "users.json");
        if (!usersFile.exists()) {
            setupUsersFile();
        }
    }

    /**
     * The activity when the user clicks on the register link
     * @param view The view
     */
    public void onClickRegister(View view) {

        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    /**
     * The activity when the user clicks on the forgot password link
     * @param view The view
     */
    public void onClickForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);

    }

    /**
     * The activity when the user clicks on the Submit Button
     * @param view The view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSubmit(View view) {

        StoredUserList storedUsers = jh.loadUsersFile(usersFile);

        String loginEmailText = etLoginEmail.getText().toString();
        String loginPasswordText = etLoginPassword.getText().toString();

        User currentUser = storedUsers.checkLoginDetails(loginEmailText, loginPasswordText);

        if (currentUser != null){
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();

            // Save a file with current user details so all activities can access without having to pass with intents
            File file = new File(getFilesDir(), "current_user.json");
            jh.saveCurrentUserToFile(file, currentUser);

            if (currentUser.getAccessLevel().equals("user")) {
                Intent intent = new Intent(this, UserHome.class);
                startActivity(intent);
            } else if (currentUser.getAccessLevel().equals("admin")) {
                Intent intent = new Intent(this, AdminHome.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creates a default users file from assets folder
     */
    public void setupUsersFile(){
        InputStream is = null;
        try {
            is = getAssets().open("users.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            jh.setupFileFromAssets(usersFile, line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}