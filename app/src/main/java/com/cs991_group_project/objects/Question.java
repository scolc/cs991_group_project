package com.cs991_group_project.objects;

public class Question {

    private int qNum;
    private String pictureFile;
    private String qText, optA, optB, optC, optD, answer;

    public Question(){
        
    }

    // Getters and Setters


    public int getQNum() {
        return qNum;
    }

    public void setQNum(int qNum) {
        this.qNum = qNum;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getQText() {
        return qText;
    }

    public void setQText(String qText) {
        this.qText = qText;
    }

    public String getOptA() {
        return optA;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public String getOptB() {
        return optB;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public String getOptC() {
        return optC;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public String getOptD() {
        return optD;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Methods

    @Override
    public String toString(){
        return "Question " + qNum;
    }
}
