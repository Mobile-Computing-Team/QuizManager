package com.mcteam.quizmanager;

public class QuestionInfo {
    int id;
    int sectionId;
    String key;
    String statement;
    String option1;
    String option2;
    String option3;
    String option4;
    String reason;


    public QuestionInfo(int id, int sectionId, String key, String statement, String option1, String option2, String option3, String option4, String reason) {
        this.id = id;
        this.sectionId = sectionId;
        this.key = key;
        this.statement = statement;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.reason = reason;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getKey() {
        return key;
    }

    public String getStatement() {
        return statement;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getReason() {
        return reason;
    }
}
