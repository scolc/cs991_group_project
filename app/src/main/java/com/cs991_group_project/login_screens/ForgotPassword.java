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
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.StoredUserList;

import java.io.File;
import java.util.regex.Pattern;


public class ForgotPassword extends AppCompatActivity {

    private EditText recEmail;
    private File usersFile;
    private JSONHandler jh;
    private Pattern regexPattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        recEmail = findViewById(R.id.forgotpw_email);
        usersFile = new File(getFilesDir(), "users.json");
        jh = new JSONHandler();

        String emailValidRegEx = "^[A-Za-z0-9+_.-]+@(.+)$";
        regexPattern = Pattern.compile(emailValidRegEx);



    }

    /**
     * The activity when the user clicks on the Back button
     * @param view The view
     */
    public void onClickBack(View view){

        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    public void onClickSubmit(View view) {

        String recEmailText = recEmail.getText().toString();

        StoredUserList storedUsers = jh.loadUsersFile(usersFile);

        if (recEmailText.isEmpty()) {
            Toast.makeText(this, R.string.forgotpw_missing_email, Toast.LENGTH_SHORT).show();
        } else if (!recEmailText.matches(String.valueOf(regexPattern))) {
            Toast.makeText(this, R.string.forgotpw_incorrect_format, Toast.LENGTH_SHORT).show();

        } else {
            if (storedUsers.checkEmailExists(recEmailText)) {
                Intent intent = new Intent(this, ForgotPasswordLinkSent.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.forgotpw_email_not_found, Toast.LENGTH_SHORT).show();


            }
        }
    }


}