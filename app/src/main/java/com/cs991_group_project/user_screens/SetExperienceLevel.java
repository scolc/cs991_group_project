package com.cs991_group_project.user_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;
import com.cs991_group_project.objects.User;

import java.io.File;
import java.util.ArrayList;

public class SetExperienceLevel extends AppCompatActivity {

    private File currentUsersFile, userDataFile, languageFile;
    private JSONHandler jh;
    private String languageName;
    private Language language;
    private int languageIndex, lessonIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_experience_level);

        languageIndex = getIntent().getIntExtra("index", 0);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");
        User user = jh.loadCurrentUserFromFile(currentUsersFile);
        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");
        CurrentUser thisUser = jh.loadUserDataFile(userDataFile);

        ArrayList<String> languageList = new ArrayList<>();


        for (Language language : thisUser.getLanguages()) {
            String languageString = language.getLangName();
            languageList.add(languageString);
        }

        Spinner spinner = findViewById(R.id.sp_languageSelect);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, languageList);

        spinner.setAdapter(ad);
    }

    public void onClickSubmit(View view) {
        finish();
    }

}