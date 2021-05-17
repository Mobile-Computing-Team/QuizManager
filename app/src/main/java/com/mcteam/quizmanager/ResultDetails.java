package com.mcteam.quizmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mcteam.quizmanager.adapterM023.RecViewAdpaterForSub;
import com.mcteam.quizmanager.adapterM023.ResultListAdapter;

public class ResultDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);

        ListView listView=findViewById(R.id.result_list_view);
        ResultListAdapter adapter=new ResultListAdapter(this, RecViewAdpaterForSub.quizQuestions,StartQuizM023.userAnswers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ResultDetails.this,"Show me everything",Toast.LENGTH_SHORT).show();

                //now show details
                AlertDialog.Builder builder=new AlertDialog.Builder(ResultDetails.this);
                builder.setTitle("Details");
                //builder.setMessage("Info of subject");
                builder.setCancelable(false);

                LayoutInflater inflater=LayoutInflater.from(ResultDetails.this);
                View layoutView=inflater.inflate(R.layout.show_details_m023,null);
                builder.setView(layoutView);

                //mcqCount=subList.get(i).getQuizMCQs()>subList.get(i).getTotalQuestions()?subList.get(i).getTotalQuestions():subList.get(i).getQuizMCQs();
                ((TextView)layoutView.findViewById(R.id.question_title)).setText(RecViewAdpaterForSub.quizQuestions.get(position).statement);
                ((RadioButton)layoutView.findViewById(R.id.choice1)).setText(RecViewAdpaterForSub.quizQuestions.get(position).option1);
                ((RadioButton)layoutView.findViewById(R.id.choice2)).setText(RecViewAdpaterForSub.quizQuestions.get(position).option2);
                ((RadioButton)layoutView.findViewById(R.id.choice3)).setText(RecViewAdpaterForSub.quizQuestions.get(position).option3);
                ((RadioButton)layoutView.findViewById(R.id.choice4)).setText(RecViewAdpaterForSub.quizQuestions.get(position).option4);
                ((TextView)layoutView.findViewById(R.id.correct_answer_key)).setText(RecViewAdpaterForSub.quizQuestions.get(position).key);
                ((TextView)layoutView.findViewById(R.id.reason)).setText(RecViewAdpaterForSub.quizQuestions.get(position).reason);
                //String value="Total Time : "+String.format("%02dH %02dM %02dS",subList.get(i).getHours(),subList.get(i).getMinutes(),subList.get(i).getSeconds());
                //((TextView)view.findViewById(R.id.timeTextView)).setText(value);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Dialog dialog= builder.create();
                dialog.show();
            }
        });
    }
}