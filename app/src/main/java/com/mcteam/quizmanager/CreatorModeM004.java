package com.mcteam.quizmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CreatorModeM004 extends AppCompatActivity {
RecyclerView recyclerView;
RecyclerAdapter adapter;
ArrayList<SubjectInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_mode_m004);
        list=new ArrayList<SubjectInfo>();
        list.add(new SubjectInfo("Maths"));
        list.add(new SubjectInfo("Physics"));
        list.add(new SubjectInfo("Chemistry"));
        list.add(new SubjectInfo("Biology"));
        list.add(new SubjectInfo("English"));
        list.add(new SubjectInfo("Psychology"));
        list.add(new SubjectInfo("Astronomy"));
        list.add(new SubjectInfo("Economics"));
        list.add(new SubjectInfo("Urdu"));
        list.add(new SubjectInfo("Islamiyat"));
        recyclerView=findViewById(R.id.section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }
}