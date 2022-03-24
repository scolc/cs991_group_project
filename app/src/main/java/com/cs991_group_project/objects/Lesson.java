package com.cs991_group_project.objects;

import java.util.ArrayList;

public class Lesson {

    private int lessonNum, lessonScore;
    private boolean lessonComplete;
    private ArrayList<Question> questions;

    public Lesson() {

    }

    // Getters and Setters

    public int getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(int lessonNum) {
        this.lessonNum = lessonNum;
    }

    public int getLessonScore() {
        return lessonScore;
    }

    public void setLessonScore(int lessonScore) {
        this.lessonScore = lessonScore;
    }

    public boolean isLessonComplete() {
        return lessonComplete;
    }

    public void setLessonComplete(boolean lessonComplete) {
        this.lessonComplete = lessonComplete;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question){
        this.questions.add(question);
    }
}
