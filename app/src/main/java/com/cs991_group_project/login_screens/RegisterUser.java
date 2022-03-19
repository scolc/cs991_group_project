package com.cs991_group_project.login_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.StoredUserList;
import com.cs991_group_project.objects.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegisterUser extends AppCompatActivity {

    private EditText etRegisterName;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private EditText etRegisterConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        etRegisterName = findViewById(R.id.et_register_name);
        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterConfirmPassword = findViewById(R.id.et_register_confirm_password);
    }

    /**
     * The activity when the user clicks on the Back button
     * @param view The view
     */
    public void onClickBack(View view){

        finish();
    }

    /**
     * The activity when the user clicks on the sign up button
     * @param view The view
     */
    public void onClickSubmit(View view) {

        String regNameText = etRegisterName.getText().toString();
        String regEmailText = etRegisterEmail.getText().toString();
        String regPasswordText = etRegisterPassword.getText().toString();
        String regPasswordConfirmText = etRegisterConfirmPassword.getText().toString();

        StoredUserList storedUsers = getStoredUsers();

        if (regNameText.isEmpty() || regEmailText.isEmpty() || regPasswordText.isEmpty() || regPasswordConfirmText.isEmpty()) {
            Toast.makeText(this, R.string.missing_details, Toast.LENGTH_SHORT).show();
        } else if (!regPasswordText.equals(regPasswordConfirmText)){
            Toast.makeText(this, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
        } else {
            if (storedUsers.checkEmailExists(regEmailText)) {
                Toast.makeText(this, R.string.UserAlreadyExists, Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(regEmailText, regPasswordText, regNameText, "user");
                storedUsers.addNewUser(newUser);
                saveStoredUsers(storedUsers);
                finish();
            }
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

    /**
     * Stores the user list in a text file in local storage
     * @param storedUsers The filled UserList object
     */
    public void saveStoredUsers(StoredUserList storedUsers){

        File file = new File(getFilesDir(), "users.txt");
        storedUsers.saveStoredUsersList(file);
        Toast.makeText(this, R.string.new_user_created, Toast.LENGTH_LONG).show();
    }
}