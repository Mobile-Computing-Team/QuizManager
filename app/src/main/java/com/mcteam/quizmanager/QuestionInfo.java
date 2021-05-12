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
        this.reason = (reason.isEmpty()?"None":reason);
    }
}
