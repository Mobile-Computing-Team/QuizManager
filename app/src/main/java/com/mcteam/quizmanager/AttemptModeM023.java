package com.mcteam.quizmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.mcteam.quizmanager.adapterM023.RecViewAdpaterForSub;

import java.util.ArrayList;

public class AttemptModeM023 extends AppCompatActivity {


    RecyclerView recyclerView;
    RecViewAdpaterForSub recyclerViewAdapter;
    ArrayList<SubjectInfo> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_mode_m023);

        //this subject arrayList will be filled from Database; e.g subject= DBHelper.getAllSubjects()
        subjects=DBHelper.getSubjectList();
        //

         if(subjects.size()>0)
         {
             //Initialization
             recyclerView = findViewById(R.id.recViewForSubjects);
             recyclerView.setHasFixedSize(true);
             recyclerView.setLayoutManager(new LinearLayoutManager(this));

             //setting adapter
             recyclerViewAdapter = new RecViewAdpaterForSub(AttemptModeM023.this, subjects);
             recyclerView.setAdapter(recyclerViewAdapter);
         }
         else
             Toast.makeText(this,"There is no quiz added by your tutor yet. Contact him for further query.",Toast.LENGTH_SHORT).show();
    }
}