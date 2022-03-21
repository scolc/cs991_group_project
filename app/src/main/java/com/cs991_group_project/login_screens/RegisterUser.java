package com.cs991_group_project.login_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.StoredUserList;
import com.cs991_group_project.objects.User;

import java.io.File;
import java.util.Calendar;

public class RegisterUser extends AppCompatActivity {

    private EditText etRegisterName;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private EditText etRegisterConfirmPassword;
    private File usersFile;
    private JSONHandler jh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        etRegisterName = findViewById(R.id.et_register_name);
        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterConfirmPassword = findViewById(R.id.et_register_confirm_password);

        usersFile = new File(getFilesDir(), "users.json");
        jh = new JSONHandler();
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSubmit(View view) {

        String regNameText = etRegisterName.getText().toString();
        String regEmailText = etRegisterEmail.getText().toString();
        String regPasswordText = etRegisterPassword.getText().toString();
        String regPasswordConfirmText = etRegisterConfirmPassword.getText().toString();

        StoredUserList storedUsers = jh.loadUsersFile(usersFile);

        if (regNameText.isEmpty() || regEmailText.isEmpty() || regPasswordText.isEmpty() || regPasswordConfirmText.isEmpty()) {
            Toast.makeText(this, R.string.missing_details, Toast.LENGTH_SHORT).show();
        } else if (!regPasswordText.equals(regPasswordConfirmText)){
            Toast.makeText(this, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
        } else {
            if (storedUsers.checkEmailExists(regEmailText)) {
                Toast.makeText(this, R.string.UserAlreadyExists, Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(regEmailText, regPasswordText, regNameText, "user");
                storedUsers.addUser(newUser);
                jh.saveUsersFile(usersFile, storedUsers);

                CurrentUser currentUser = new CurrentUser();
                currentUser.setEmail(newUser.getEmail());
                currentUser.setUName(newUser.getUName());
                currentUser.setJoinDateFromDate(Calendar.getInstance());
                File userDataFile = new File(getFilesDir(), newUser.getEmail() + "_data.json");
                jh.saveUserDataFile(userDataFile, currentUser);

                finish();
            }
        }
    }
}