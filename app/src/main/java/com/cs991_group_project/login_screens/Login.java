package com.cs991_group_project.login_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.admin_screens.AdminHome;
import com.cs991_group_project.objects.StoredUserList;
import com.cs991_group_project.objects.User;
import com.cs991_group_project.user_screens.UserHome;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Login extends AppCompatActivity {

    private EditText etLoginEmail;
    private EditText etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
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

    }

    /**
     * The activity when the user clicks on the Submit Button
     * @param view The view
     */
    public void onClickSubmit(View view) {

        StoredUserList storedUsers = getStoredUsers();

        String loginEmailText = etLoginEmail.getText().toString();
        String loginPasswordText = etLoginPassword.getText().toString();

        User currentUser = storedUsers.checkLoginDetails(loginEmailText, loginPasswordText);

        if (currentUser != null){
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();

            // Save a file with current user details so all activities can access without having to pass with intents
            File file = new File(getFilesDir(), "current_user.txt");
            currentUser.saveCurrentUserToFile(file);

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
     * Fetches the text file with store users from storage and loads it into a UserList object
     * @return The filled UserList object
     */
    public StoredUserList getStoredUsers() {

        FileInputStream fis = null;
        InputStream is = null;
        StoredUserList storedUsers = new StoredUserList();

        try {
            fis = openFileInput("users.txt");
            storedUsers.openStoredUsersList(fis);
            fis.close();
        } catch (IOException e) {
            // File not found in local storage so use the default one in assets
            try {
                is = getAssets().open("users.txt");
                storedUsers.openStoredUsersList(is);
                is.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return storedUsers;
    }
}