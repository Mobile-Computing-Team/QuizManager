package com.mcteam.quizmanager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper {
    boolean addSubject(SubjectInfo subjectInfo)
    {
        return true;
    }
    boolean removeSubject(int subjectId)
    {
        return true;
    }
    boolean updateSubject(SubjectInfo subjectInfo)
    {
        return true;
    }
    boolean addQuestion(QuestionInfo questionInfo)
    {
        return true;
    }
    boolean removeQuestion(int QuestionId)
    {
        return true;
    }
    boolean updateQuestion(QuestionInfo questionInfo)
    {
        return true;
    }
    ArrayList<SubjectInfo>getSubjectList()
    {
        return new ArrayList<SubjectInfo>();
    }
    ArrayList<QuestionInfo>getQuestionsListOfSubject(int subjectId)
    {
        return new ArrayList<QuestionInfo>();
    }
}
