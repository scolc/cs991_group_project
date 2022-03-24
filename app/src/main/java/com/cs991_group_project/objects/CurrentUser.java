package com.cs991_group_project.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CurrentUser extends User{

    private String joinDate;
    private ArrayList<Language> languages;

    public CurrentUser() {
        super();
        this.languages = new ArrayList<>();
    }

    // Getters and Setters

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    public boolean addLanguage(Language language) {

        if (languages.contains(language)) {
            return false;
        } else {
            this.languages.add(language);
            return true;
        }
    }

    public void setJoinDateFromDate(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        joinDate =  formatter.format(calendar.getTime());
    }
}
