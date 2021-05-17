package com.mcteam.quizmanager.adapterM023;

import java.util.ArrayList;

public class BundleM023 {
    int currQues;
    int[]userAnswers;
    boolean[]flagQues;
    ArrayList<String> solved;
    boolean finishFlag,submitFlag;
    long tempSec;
    int hour;
    int min;
    int sec;

    public long getTempSec() {
        return tempSec;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public void setTempSec(long tempSec) {
        this.tempSec = tempSec;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public BundleM023(int currQues, int[] userAnswers, boolean[] flagQues, ArrayList<String> solved, boolean finishFlag, boolean submitFlag, long tempSec) {
        this.currQues = currQues;
        this.userAnswers = userAnswers;
        this.flagQues = flagQues;
        this.solved = solved;
        this.finishFlag = finishFlag;
        this.submitFlag = submitFlag;
        this.tempSec=tempSec;
    }

    public void setCurrQues(int currQues) {
        this.currQues = currQues;
    }

    public void setUserAnswers(int[] userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void setFlagQues(boolean[] flagQues) {
        this.flagQues = flagQues;
    }

    public void setSolved(ArrayList<String> solved) {
        this.solved = solved;
    }

    public void setFinishFlag(boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public void setSubmitFlag(boolean submitFlag) {
        this.submitFlag = submitFlag;
    }

    public int getCurrQues() {
        return currQues;
    }

    public int[] getUserAnswers() {
        return userAnswers;
    }

    public boolean[] getFlagQues() {
        return flagQues;
    }

    public ArrayList<String> getSolved() {
        return solved;
    }

    public boolean isFinishFlag() {
        return finishFlag;
    }

    public boolean isSubmitFlag() {
        return submitFlag;
    }
}
