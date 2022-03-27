package com.cs991_group_project.admin_screens;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminCreateLanguage extends AppCompatActivity {

    private EditText name;
    private File availableLanguagesFile;
    private JSONHandler jh;
    private ArrayList<String> languages;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_language);

        name = findViewById(R.id.et_add_language_name);

        jh = new JSONHandler();
        availableLanguagesFile = new File(getFilesDir(), "languages.json");

        if (!availableLanguagesFile.exists()){
            setupAvailableLanguages();
        }

        languages = jh.loadAvailableLanguages(availableLanguagesFile);
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

    public void onClickSubmit(View view) {

        String chosenName = name.getText().toString();
        if (languages.contains(chosenName)) {
            Toast.makeText(this, R.string.language_exists, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, AdminCreateLesson.class);
            intent.putExtra("name", chosenName);
            startActivity(intent);
        }
    }
}