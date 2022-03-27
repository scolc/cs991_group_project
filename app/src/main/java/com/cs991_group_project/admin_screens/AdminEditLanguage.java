package com.cs991_group_project.admin_screens;

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

import com.cs991_group_project.R;
import com.cs991_group_project.objects.JSONHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminEditLanguage extends AppCompatActivity {

    private File availableLanguagesFile;
    private JSONHandler jh;
    private int index;
    private ArrayList<String> languages;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_language);

        jh = new JSONHandler();
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
                Button button = findViewById(R.id.bt_continue);
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

    public void onClickContinue(View view) {
        Intent intent = new Intent(this, AdminEditLesson.class);
        intent.putExtra("name", languages.get(index));
        startActivity(intent);
    }
}