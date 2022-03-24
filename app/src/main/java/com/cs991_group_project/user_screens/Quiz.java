package com.cs991_group_project.user_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;
import com.cs991_group_project.objects.Question;
import com.cs991_group_project.objects.User;

import java.io.File;

public class Quiz extends AppCompatActivity {

    private File currentUsersFile, userDataFile, languageFile;
    private JSONHandler jh;
    private int languageIndex, lessonIndex, qIndex, score;
    private Question currentQuestion;
    private Lesson currentLesson;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        languageIndex = getIntent().getIntExtra("languageIndex", 0);
        lessonIndex = getIntent().getIntExtra("lessonIndex", 0);
        qIndex = getIntent().getIntExtra("qIndex", 0);
        score = getIntent().getIntExtra("score", 0);

        jh = new JSONHandler();
        currentUsersFile = new File(getFilesDir(), "current_user.json");

        User user = jh.loadCurrentUserFromFile(currentUsersFile);

        userDataFile = new File(getFilesDir(), user.getEmail() + "_data.json");

        CurrentUser thisUser = jh.loadUserDataFile(userDataFile);
        String languageName = thisUser.getLanguages().get(languageIndex).getLangName();

        languageFile = new File(getFilesDir(), languageName + ".json");

        Language currentLanguage = jh.loadLanguage(languageFile);
        currentLesson = currentLanguage.getLessons().get(lessonIndex);
        currentQuestion = currentLesson.getQuestions().get(qIndex);

        TextView tvQuestionNum = findViewById(R.id.tv_question_num);
        tvQuestionNum.setText(languageName + " " + currentLesson.toString() + " " + currentQuestion.toString());

    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickHome(View view) {
        Intent intent = new Intent(this, UserHome.class);
        startActivity(intent);
    }

    public void onClickMenu(View view) {
    }
}