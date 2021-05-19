package com.mcteam.quizmanager;

import android.util.Log;

import androidx.annotation.Nullable;

public class SubjectInfo{
    String title;
    int totalQuestions=0;
    int quizMCQs=0;
    int hours;
    int minutes;
    int seconds;


    public SubjectInfo(String title) {
        this.title = title;
    }

    public SubjectInfo(String title, int totalQuestions, int quizMCQs) {
        this.title = title;
        this.totalQuestions = totalQuestions;
        this.quizMCQs = quizMCQs;
    }

    public SubjectInfo(String title, int totalQuestions, int quizMCQs, int hours, int minutes, int seconds) {
        this.title = title;
        this.totalQuestions = totalQuestions;
        this.quizMCQs = quizMCQs;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setQuizMCQs(int quizMCQs) {
        this.quizMCQs = quizMCQs;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getQuizMCQs() {
        return quizMCQs;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}
