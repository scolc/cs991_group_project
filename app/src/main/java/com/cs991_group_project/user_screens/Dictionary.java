package com.cs991_group_project.user_screens;

import static android.widget.AdapterView.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.JSONHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
//import android.widget.AdapterView.OnItemSelectedListener;



public class Dictionary extends AppCompatActivity {

    private File availableLanguagesFile;
    private JSONHandler jh;
    private ArrayList<String> languages;
    private EditText wordInput;
    private Spinner sourceSpinner;
    private Spinner targetSpinner;
    private TextView dicResult;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        jh = new JSONHandler();
        availableLanguagesFile = new File(getFilesDir(), "languages.json");


        languages = jh.loadAvailableLanguages(availableLanguagesFile);

        sourceSpinner = (Spinner) findViewById(R.id.src_lang_spinner);

        targetSpinner = (Spinner) findViewById(R.id.target_lang_spinner);

        dicResult = (TextView) findViewById(R.id.dic_result);

        wordInput = (EditText) findViewById(R.id.dic_wordToSearch);





    }

    /**
     * The activity when the user clicks on the Back button
     * @param view The view
     */
    public void onClickBack(View view){

        finish();
    }

    public String bold(String text) {
        return String.format("<b>%s</b>", text);
    }


    public void onClickTransl(View view) {
        String srcLang = String.valueOf(sourceSpinner.getSelectedItem());
        String tgtLang = String.valueOf(targetSpinner.getSelectedItem());

        if (srcLang.equals("English") && tgtLang.equals("Spanish")) {
            wordInput.setText("aardvark");
            dicResult.setText("cerdo hormiguero {m}\n{n} /ˈɑɹdˌvɑɹk/ (mammal)");


        } else if (srcLang.equals("Spanish") && tgtLang.equals("English")) {
            wordInput.setText("estocada");
            dicResult.setText("thrust (stab) {f}\n{n} thrust (stab) by a rapier or smallsword; wound caused by such thrust");

        } else if (srcLang.equals(tgtLang)) {
            Toast.makeText(this, "Please select two different languages!", Toast.LENGTH_SHORT).show();
        }



    }
}

