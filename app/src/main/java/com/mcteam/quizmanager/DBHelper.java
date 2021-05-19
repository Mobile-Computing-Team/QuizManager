package com.mcteam.quizmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mcteam.quizmanager.adapterM023.BundleM023;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    /*TODO:BSEF18M046!A very important note for you
    I have added some comments in those functions which
    you are going to implement for facilitation. I have
    added some references in the comments in case if I am
    mentioning some things outside the comment.
    Like Inside Comments:
    1. The words surrounded by "" are actually
    variable names or types which are to be used in your code.
    2. The words surrounded by ** are actually referring to
    column names of the database tables. To see column names
    please refer to the string arrays I have created below.*/
    //Following are the variables which I have created for
    //use in database operations and you must use these
    //variables wherever you need to use table or column name
    //TODO: BSEF18M046! Another Important Note for you DO NOT USE HARDCODED TABLE NAMES OR COLUMN NAMES IN YOUR FUNCTIONS
    public static final String DATABASE_NAME="MC_GROUP_PROJECT_DATABASE.db";
    public static final String TABLE1_NAME="Subjects";
    public static final String[] TABLE1_COLUMNS={"Id","Title","Quiz_MCQs","Quiz_Time_Hour","Quiz_Time_Minute","Quiz_Time_Second"};
    public static final String TABLE2_NAME="Questions";
    public static final String[] TABLE2_COLUMNS={"Id","Subject_Id","Statement","Option_A","Option_B","Option_C","Option_D","Key_Option","Reason"};
    public static final String TABLE3_NAME="Creator_Password";
    public static final String[] TABLE3_COLUMNS={"Password"};
    static BundleM023 tempStorage;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creating these Variables to avoid an error
        String temp1=TABLE1_COLUMNS[0];
        String temp2=TABLE2_COLUMNS[0];
        String temp3=TABLE3_COLUMNS[0];
        sqLiteDatabase.execSQL("create table "+TABLE1_NAME+" ("+temp1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TABLE1_COLUMNS[1]+" TEXT,"+TABLE1_COLUMNS[2]+" INTEGER,"+TABLE1_COLUMNS[3]+" INTEGER,"+TABLE1_COLUMNS[4]+" INTEGER,"+TABLE1_COLUMNS[5]+" INTEGER);");
        sqLiteDatabase.execSQL("create table "+TABLE2_NAME+" ("+temp2+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TABLE2_COLUMNS[1]+" INTEGER,"+TABLE2_COLUMNS[2]+" TEXT,"+TABLE2_COLUMNS[3]+" TEXT,"+TABLE2_COLUMNS[4]+" TEXT,"+TABLE2_COLUMNS[5]+" TEXT,"+TABLE2_COLUMNS[6]+" TEXT,"+TABLE2_COLUMNS[7]+" TEXT,"+TABLE2_COLUMNS[8]+" TEXT,FOREIGN KEY("+TABLE2_COLUMNS[1]+") REFERENCES "+TABLE1_NAME+"("+TABLE1_COLUMNS[0]+"));");
        sqLiteDatabase.execSQL("create table "+TABLE3_NAME+" ("+temp3+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE1_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE2_NAME);
        sqLiteDatabase.execSQL("drop table if exists "+TABLE3_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean isCreator(String Password)
    {
        //TODO: BSEF18M046, Function No.1
        /*This function will search the *Password* in
        database table "TABLE3_NAME" if found returns
        true otherwise false*/
        return true;
    }
    public boolean addSubject(SubjectInfo newSubjectInfo)
    {
        //TODO: BSEF18M046, Function No.2
        /*This function will insert subject
        "newSubjectInfo" to database table "TABLE1_NAME"*/
        return true;
    }
    public boolean removeSubject(int subjectId)
    {
        //TODO: BSEF18M046, Function No.3
        /*This function will remove subject
        having *Id* "subjectId" from database
        table "TABLE1_NAME"*/
        return true;
    }
    public boolean updateSubject(int subjectId, SubjectInfo updatedSubjectInfo)
    {
        //TODO: BSEF18M046, Function No.4
        /*This function will update subject having
        *Id* "subjectId" to "updatedSubjectInfo" in database
        table "TABLE1_NAME"*/
        return true;
    }
    public boolean addQuestion(QuestionInfo newQuestionInfo)
    {
        //TODO: BSEF18M046, Function No.5
        /*This function will insert question
        "newQuestionInfo" to database table "TABLE2_NAME"*/
        return true;
    }
    public boolean removeQuestion(int questionId)
    {
        //TODO: BSEF18M046, Function No.6
        /*This function will remove question
        having *Id* "questionId" from database
        table "TABLE2_NAME"*/
        return true;
    }
    public boolean updateQuestion(int questionId, QuestionInfo updatedQuestionInfo)
    {
        //TODO: BSEF18M046, Function No.7
        /*This function will update question having
        *Id* "questionId" to "updatedQuestionInfo" in database
        table "TABLE2_NAME"*/
        return true;
    }
    public ArrayList<SubjectInfo> getSubjectsList()
    {
        //TODO: BSEF18M046, Function No.8
        /*This function will retrieve all subjects
        from database table "TABLE1_NAME" in the form
        of an "ArrayList<SubjectInfo>"*/
        return new ArrayList<SubjectInfo>();
    }
    public ArrayList<QuestionInfo>getQuestionsListOfSubject(int subjectId)
    {
        //TODO: BSEF18M046, Function No.9
        /*This function will retrieve all questions
        having *Subject_Id* "subjectId" from database
        table "TABLE2_NAME" in the form of an
        "ArrayList<QuestionInfo>"*/
        return new ArrayList<QuestionInfo>();
    }

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