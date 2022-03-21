package com.cs991_group_project.objects;

import java.util.ArrayList;

public class Language {

    private String langName;
    private ArrayList<Lesson> lessons;

    public Language() {

        this.langName = null;
        this.lessons = new ArrayList<>();
    }

    // Getters and Setters

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
}
