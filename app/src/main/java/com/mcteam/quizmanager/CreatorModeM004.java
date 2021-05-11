package com.mcteam.quizmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        recyclerView=findViewById(R.id.section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerAdapter(this,list,(TextView) findViewById(R.id.message));
        recyclerView.setAdapter(adapter);
    }
    int findByTitle(String title)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).title.equals(title))
            {
                return i;
            }
        }
        return -1;
    }

    public void AddSubject(View view) {
        final View dialogView=getLayoutInflater().inflate(R.layout.change_title_dialog,null);
        final EditText dialogEditText=dialogView.findViewById(R.id.sectionTitle);
        final AlertDialog dialog=new AlertDialog.Builder(this).setView(dialogView).setPositiveButton("Create",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setTitle("Create New Subject").create();
        dialog.show();
        Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String newTitle=dialogEditText.getText().toString();
                    if(newTitle.isEmpty())
                    {
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                        ((TextView)dialogView.findViewById(R.id.error)).setText("Title Can't be Empty!");
                    }
                    else if(findByTitle(newTitle)==-1) {
                        ((TextView)findViewById(R.id.message)).setText("");
                        list.add(0, new SubjectInfo(newTitle));
                        adapter.notifyItemInserted(0);
                        recyclerView.scrollToPosition(0);
                        dialog.dismiss();
                    }else{
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE); }
            }
        });
    }
}