//This adapter is used to show subjects list which will be selected by user to attempt quiz

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

import com.mcteam.quizmanager.DBHelper;
import com.mcteam.quizmanager.QuestionInfo;
import com.mcteam.quizmanager.R;
import com.mcteam.quizmanager.StartQuizM023;
import com.mcteam.quizmanager.SubjectInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class RecViewAdpaterForSub extends RecyclerView.Adapter<RecViewAdpaterForSub.ViewHolder>{

    public static Context context;
    ArrayList<SubjectInfo> subList;
    int i; //index that will point to selected subject
    public static ArrayList<QuestionInfo> allQuestions;
    public static ArrayList<QuestionInfo> quizQuestions;
    public static int mcqCount;


    public RecViewAdpaterForSub(Context context, ArrayList<SubjectInfo> arrayList) {
        this.context = context;
        this.subList = arrayList;
        allQuestions=DBHelper.getQuestionsListOfSubject();
        quizQuestions=new ArrayList<QuestionInfo>();
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
        String subText=subList.get(position).getTitle();
        holder.subText.setText(subText);
    }

    @Override
    public int getItemCount() {
        return subList.size();
    }

    public void generateRandomQuiz()
    {
        Random rand=new Random();
        for(int i=0;i<mcqCount;i++)
        {
            //randomly select position
            int position=rand.nextInt(allQuestions.size());
            quizQuestions.add(allQuestions.get(position));
            allQuestions.remove(position); //mcq is selected now remove it from list, size will be decremented
        }
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


            //search index of clickable element in array
            i=0;
            for(;i<subList.size();i++)
            {
                if(subList.get(i).getTitle()==subText.getText())
                    break;   //it will always found in arrayList
            }

            //make a dialogue
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle(subText.getText());
            //builder.setMessage("Info of subject");
            builder.setCancelable(false);

            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.quiz_info_m023,null);
            builder.setView(view);
            mcqCount=subList.get(i).getQuizMCQs()>subList.get(i).getTotalQuestions()?subList.get(i).getTotalQuestions():subList.get(i).getQuizMCQs();
            ((TextView)view.findViewById(R.id.mcqTextView)).setText("Total MCQ  : "+mcqCount);
            String value="Total Time : "+String.format("%02dH %02dM %02dS",subList.get(i).getHours(),subList.get(i).getMinutes(),subList.get(i).getSeconds());
            ((TextView)view.findViewById(R.id.timeTextView)).setText(value);
            builder.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //This button will be enable just when quiz is ready now move to other page or activity
                    //First of all you hae to made there a quiz, try to make them static so you can access them in paper
                    //activity
                    allQuestions= DBHelper.getQuestionsListOfSubject();
                    generateRandomQuiz(); //after calling this function,random quiz is populated in arrayList named quizQuestions
                                          //now access that arraList in your new activity and display questions on screen
                    Intent intent=new Intent(context, StartQuizM023.class);
                    intent.putExtra("hour",subList.get(i).getHours());
                    intent.putExtra("min",subList.get(i).getMinutes());
                    intent.putExtra("sec",subList.get(i).getSeconds());
                    context.startActivity(intent);
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
           if(subList.get(i).getTotalQuestions()<1 || subList.get(i).getQuizMCQs()<1)           //either no question available in DB, or tutor set limit 0
           {
             ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
             ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setText("Not Available");
           }
        }
    }
}