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
}
