package com.cs991_group_project.admin_screens;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AdminCreateLesson extends AppCompatActivity {

    private String name, jName;
    private boolean isNew;
    private Uri sUri;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Language currentLanguage, newLanguage;
    private File currentLangFile, newLangFile, availableLanguagesFile;
    private JSONHandler jh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_lesson);

        name = getIntent().getStringExtra("name");
        isNew = getIntent().getBooleanExtra("isNew", true);

        TextView tvLanguage = findViewById(R.id.tv_language);
        tvLanguage.setText("Language: " + name);

        jh = new JSONHandler();

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null){
                        sUri = data.getData();
                        Button btUpload = findViewById(R.id.bt_upload);
                        btUpload.setVisibility(View.INVISIBLE);
                        Button btSubmit = findViewById(R.id.bt_submit);
                        btSubmit.setEnabled(true);
                        TextView tv = findViewById(R.id.tv_upload_lesson);
                        tv.setText(sUri.getPath());
                    }
                }
            }
        });
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, AdminAddLessonLanguageSelect.class);
        startActivity(intent);
    }

    public void onClickUpload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/zip");
        intent = Intent.createChooser(intent, "Choose A File");
        resultLauncher.launch(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSubmit(View view) {

        parseZipFile();
        parseLessonFile();

        Intent intent = new Intent(this, AdminAddLessonLanguageSelect.class);
        startActivity(intent);

    }

    public void parseZipFile() {
        try {

            InputStream is = getContentResolver().openInputStream(sUri);
            OutputStream os = new FileOutputStream(getFilesDir() + "/temp.zip");

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            is.close();
            os.close();

            ZipFile zipFile = new ZipFile(getFilesDir() + "/temp.zip");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String fileName = entry.getName();

                if (fileName.contains("json")) {
                    jName = fileName;
                }

                FileOutputStream fos = new FileOutputStream(getFilesDir() + "/" + fileName);
                is = zipFile.getInputStream(entry);

                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                is.close();
                fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void parseLessonFile() {
        newLangFile = new File(getFilesDir(), jName);
        newLanguage = jh.loadLanguage(newLangFile);
        currentLangFile = new File(getFilesDir(), name + ".json");

        if (isNew) {
            currentLanguage = new Language();
            currentLanguage.setLangName(name);
            availableLanguagesFile = new File(getFilesDir(), "languages.json");
            ArrayList<String> languages = jh.loadAvailableLanguages(availableLanguagesFile);
            languages.add(name);
            jh.saveAvailableLanguages(availableLanguagesFile, languages);
        } else {
            currentLanguage = jh.loadLanguage(currentLangFile);
        }

        for (Lesson lesson : newLanguage.getLessons()) {
            lesson.setLessonNum(currentLanguage.getLessons().size() + 1);
            currentLanguage.addLesson(lesson);
        }

        jh.saveLanguageFile(currentLangFile, currentLanguage);

        Toast.makeText(this, "Lesson File Added", Toast.LENGTH_SHORT).show();
    }
}