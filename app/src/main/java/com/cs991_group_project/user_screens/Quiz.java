package com.cs991_group_project.user_screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs991_group_project.R;
import com.cs991_group_project.objects.CurrentUser;
import com.cs991_group_project.objects.JSONHandler;
import com.cs991_group_project.objects.Language;
import com.cs991_group_project.objects.Lesson;
import com.cs991_group_project.objects.Question;
import com.cs991_group_project.objects.User;

import java.io.File;
import java.io.IOException;

public class Quiz extends AppCompatActivity {

    private File currentUsersFile, userDataFile, languageFile;
    private JSONHandler jh;
    private int languageIndex, lessonIndex, qIndex, score;
    private Question currentQuestion;
    private Lesson currentLesson;
    private String currentAnswer = "";
    private TextView tvOptA, tvOptB, tvOptC, tvOptD, selected;
    private Button submitButton;

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

        /** Sets the image from file, either from assets or directory.
         * Allows for admin to add new picture files into data directory with the lesson file.
         **/
        ImageView image = findViewById(R.id.question_picture);
        String imageString = currentQuestion.getPictureFile();
        File imageFile = new File(getFilesDir(), imageString);
        Drawable drawable = null;
        if (imageFile.exists()) {
            drawable = Drawable.createFromPath(String.valueOf(imageFile));
        } else {
            try {
                drawable = Drawable.createFromStream(getAssets().open(imageString), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        image.setImageDrawable(drawable);

        TextView questionHintText = findViewById(R.id.tv_question_hint_text);
        questionHintText.setText(currentQuestion.getQText());

        tvOptA = findViewById(R.id.tv_option_a);
        tvOptB = findViewById(R.id.tv_option_b);
        tvOptC = findViewById(R.id.tv_option_c);
        tvOptD = findViewById(R.id.tv_option_d);
        selected = tvOptA;

        tvOptA.setText(currentQuestion.getOptA());
        tvOptB.setText(currentQuestion.getOptB());
        tvOptC.setText(currentQuestion.getOptC());
        tvOptD.setText(currentQuestion.getOptD());

        submitButton = findViewById(R.id.bt_submit);
    }

    public void onClickOptionA(View view) {
        updateSelected(tvOptA, "A");
    }
    public void onClickOptionB(View view) {
        updateSelected(tvOptB, "B");
    }
    public void onClickOptionC(View view) {
        updateSelected(tvOptC, "C");
    }
    public void onClickOptionD(View view) {
        updateSelected(tvOptD, "D");
    }

    public void updateSelected(TextView newSelection, String letter) {

        selected.setBackgroundResource(0);
        selected = newSelection;
        selected.setBackgroundResource(R.color.blue);
        currentAnswer = letter;
        submitButton.setEnabled(true);
    }

    public void onClickSubmit(View view) {

        String result;
        if (currentAnswer.equals(currentQuestion.getAnswer())) {
            score++;
            result = "Correct!";
        } else {
            result = "Wrong!";
        }

        result += "\nThe answer was ";

        if (currentQuestion.getAnswer().equals("A")) {
            result += currentQuestion.getOptA() + "!";
        } else if (currentQuestion.getAnswer().equals("B")) {
            result += currentQuestion.getOptB() + "!";
        } else if (currentQuestion.getAnswer().equals("C")) {
            result += currentQuestion.getOptC() + "!";
        } else {
            result += currentQuestion.getOptD() + "!";
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        qIndex ++;
        Intent intent;

        if (qIndex < currentLesson.getQuestions().size()) {
            intent = new Intent(this, Quiz.class);
            intent.putExtra("qIndex", qIndex);
        } else {
            intent = new Intent(this, LessonComplete.class);
        }
        intent.putExtra("languageIndex", languageIndex);
        intent.putExtra("lessonIndex", lessonIndex);
        intent.putExtra("score", score);

        startActivity(intent);
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