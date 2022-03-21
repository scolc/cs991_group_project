package com.cs991_group_project.objects;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {

    private String email, password, uName, accessLevel;

    public User(String email, String password, String uName, String accessLevel) {

        this.email = email;
        this.password = password;
        this.uName = uName;
        this.accessLevel = accessLevel;
    }

    public User() {

    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String name) {
        this.uName = name;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

}