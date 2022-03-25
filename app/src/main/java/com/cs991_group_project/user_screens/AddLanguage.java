package com.cs991_group_project.user_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;
import com.cs991_group_project.objects.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddLanguage extends AppCompatActivity {

    private File currentUsersFile, userDataFile, availableLanguagesFile, languageFile;
    private JSONHandler jh;
    private int index;
    private ArrayList<String> languages;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_language);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");

        User user = jh.loadCurrentUserFromFile(currentUsersFile);

        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");

        availableLanguagesFile = new File(getFilesDir(), "languages.json");

        if (!availableLanguagesFile.exists()){
            setupAvailableLanguages();
        }

        languages = jh.loadAvailableLanguages(availableLanguagesFile);

        ListView listView = findViewById(R.id.lv_languages);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, languages);

        listView.setAdapter(ad);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Button button = findViewById(R.id.bt_add_language);
                button.setEnabled(true);
            }
        });
    }

    public void setupAvailableLanguages() {
        InputStream is;
        try {
            is = getAssets().open("languages.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            jh.setupFileFromAssets(availableLanguagesFile, line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickAddLanguage(View view) {

        languageFile = new File(getFilesDir(), languages.get(index) + ".json");

        if (!languageFile.exists()) {
            setupLanguageFile();
        }
        Language selectedLanguage = jh.loadLanguage(languageFile);

        Language userLanguage = new Language();

        userLanguage.setLangName(selectedLanguage.getLangName());

        for(Lesson lesson : selectedLanguage.getLessons()){
            lesson.setLessonComplete(false);
            lesson.setLessonScore(0);
            lesson.setQuestions(null);
            userLanguage.addLesson(lesson);
        }

        CurrentUser thisUser = jh.loadUserDataFile(userDataFile);

        Boolean added = thisUser.addLanguage(userLanguage);

        if (added) {
            jh.saveUserDataFile(userDataFile, thisUser);

            Intent intent = new Intent(this, UserHome.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You already have this language", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupLanguageFile(){
        InputStream is;
        try {
            is = getAssets().open(languages.get(index) + ".json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            jh.setupFileFromAssets(languageFile, line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}