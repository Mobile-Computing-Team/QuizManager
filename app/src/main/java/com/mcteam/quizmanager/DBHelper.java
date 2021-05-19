package com.mcteam.quizmanager;

import com.mcteam.quizmanager.adapterM023.BundleM023;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper {
    static BundleM023 tempStorage;
    public static void storeBundle(BundleM023 bundleM023) {
        tempStorage=bundleM023;
    }

    public static BundleM023 getBundle() {
        //dont forget to set h,m,s according to tempSec
        tempStorage.setHour((int)tempStorage.getTempSec()/3600);
        tempStorage.setMin((int)tempStorage.getTempSec()/60);
        tempStorage.setSec((int)tempStorage.getTempSec()%60);
        return tempStorage;
    }


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
    public static ArrayList<SubjectInfo> getSubjectList()
    {
        ArrayList<SubjectInfo> list=new ArrayList<>();
        SubjectInfo subj=new SubjectInfo("English", 80, 50, 0, 30, 0);
        list.add(subj);
        subj=new SubjectInfo("Math", 5, 4, 0, 1, 6);
        list.add(subj);
        subj=new SubjectInfo("Physics", 80, 50, 2, 5, 0);
        list.add(subj);
        subj=new SubjectInfo("Astronomy", 0, 50, 2, 5, 05);
        list.add(subj);
        return list;
    }
    public static ArrayList<QuestionInfo>getQuestionsListOfSubject()
    {
        ArrayList<QuestionInfo> list=new ArrayList<>();
        list.add(new QuestionInfo(1,1,"C","2 + 2 = ?","2","3","4","5","Its natural"));
        list.add(new QuestionInfo(2,1,"B","6 + 0 = ?","60","6","0","10","Its addition"));
        list.add(new QuestionInfo(3,1,"D","2 = ?","60","6","0","2","Its addition"));
        list.add(new QuestionInfo(4,1,"A","3 - 2 = ?","1","0","00","2","Its addition"));
        list.add(new QuestionInfo(5,1,"C","3 * 2 = ?","4","0","6","2","Its addition"));
        return list;
    }
}