package com.mcteam.quizmanager.adapterM023;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcteam.quizmanager.R;
import com.mcteam.quizmanager.StartQuizM023;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecViewAdpaterForSub extends RecyclerView.Adapter<RecViewAdpaterForSub.ViewHolder>{

    Context context;
    ArrayList<String> subList;
    public static ArrayList<String> ques;

    public RecViewAdpaterForSub(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.subList = arrayList;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RecViewAdpaterForSub.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_row_m023,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecViewAdpaterForSub.ViewHolder holder, int position) {
        String subText=subList.get(position);
        holder.subText.setText(subText);
    }

    @Override
    public int getItemCount() {
        return subList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView subText;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            subText=itemView.findViewById(R.id.subText);
            subText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==subText.getId())
                Toast.makeText(context,((TextView)v).getText(),Toast.LENGTH_SHORT).show();


            //SubjectInfo subjectInfo=new SubjectInfo(1,25,0,30,0,String.valueOf(subText.getText()));
            //make a dialogue
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle(subText.getText());
            //builder.setMessage("Info of subject");
            builder.setCancelable(false);

            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.quiz_info_m023,null);
            builder.setView(view);
            ((TextView)view.findViewById(R.id.mcqTextView)).setText("MCQ Allowed : "/*+subjectInfo.getTotalMCQsAllowed()*/);
            ((TextView)view.findViewById(R.id.mcqTextView)).setText("Time Allowed: "/*+subjectInfo.getHour()+"H "+subjectInfo.getMinute()+"M "+subjectInfo.getSecond()+"S"*/);
            builder.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //This button will be enable just when quiz is ready now move to other page or activity
                    //First of all you hae to made there a quiz, try to make them static so you can access them in paper
                    //activity
                    Intent intent=new Intent(context, StartQuizM023.class);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
           Dialog dialog= builder.create();
           dialog.show();
           if(true)           //when no question available in DB
           {
             ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
             ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setText("Not Available");
           }
        }
    }
}