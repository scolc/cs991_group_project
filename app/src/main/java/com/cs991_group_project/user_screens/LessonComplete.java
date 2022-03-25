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
import com.cs991_group_project.objects.User;

import java.io.File;

public class LessonComplete extends AppCompatActivity {

    private File currentUsersFile, userDataFile, languageFile;
    private JSONHandler jh;
    private int languageIndex, lessonIndex, score;
    private Lesson currentLesson;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_complete);

        languageIndex = getIntent().getIntExtra("languageIndex", 0);
        lessonIndex = getIntent().getIntExtra("lessonIndex", 0);
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

        thisUser.getLanguages().get(languageIndex).getLessons().get(lessonIndex).setLessonComplete(true);

        TextView tvLessonScore = findViewById(R.id.tv_lesson_score);

        String result = "Lesson Score: " + score + "/" + currentLesson.getQuestions().size();
        tvLessonScore.setText(result);

        TextView tvScoreUpdate = findViewById(R.id.tv_score_update);
        String update;
        int currentScore = thisUser.getLanguages().get(languageIndex).getLessons().get(lessonIndex).getLessonScore();

        if (currentScore == score){
            update = "This matches you current best!";
        } else if (currentScore < score) {
            update = "This is your new high score!";
            thisUser.getLanguages().get(languageIndex).getLessons().get(lessonIndex).setLessonScore(score);
        } else {
            update = "Your best score is " + currentScore + "\nBetter luck next time!";
        }

        tvScoreUpdate.setText(update);
        jh.saveUserDataFile(userDataFile, thisUser);
    }

    public void onClickBackToLessons(View view) {

        Intent intent = new Intent(this, Lessons.class);
        intent.putExtra("index", languageIndex);
        startActivity(intent);
    }
}