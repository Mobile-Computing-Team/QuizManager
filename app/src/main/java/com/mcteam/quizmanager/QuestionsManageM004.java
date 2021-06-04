package com.mcteam.quizmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class QuestionsManageM004 extends AppCompatActivity {
    RecyclerView recyclerView;
    QuestionViewListAdapter adapter;
    ArrayList<QuestionInfo> list=new ArrayList<>();
    int subjectId;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_manage_m004);
        db=new DBHelper(this);
        subjectId= Objects.requireNonNull(getIntent().getExtras()).getInt("subjectId");
        list=db.getQuestionsListOfSubject(subjectId);
        if(list.isEmpty())
        {
            ((TextView)findViewById(R.id.qmessage)).setText(R.string.qmessage);
        }
        else
        {
            ((TextView)findViewById(R.id.qmessage)).setText("");
        }
        recyclerView=findViewById(R.id.question_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new QuestionViewListAdapter(this,list,(TextView)findViewById(R.id.qmessage),db,subjectId);
        recyclerView.setAdapter(adapter);
    }

    int findByStatement(String statement)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getStatement().equals(statement))
            {
                return i;
            }
        }
        return -1;
    }

    public void AddQuestion(View view) {
        final View dialogView=getLayoutInflater().inflate(R.layout.add_question_dialog,null);
        final AlertDialog dialog=new AlertDialog.Builder(this).setView(dialogView).setPositiveButton("Add",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setTitle("Add New Question").create();
        dialog.show();
        Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String statementInput=((EditText)dialogView.findViewById(R.id.statement_input)).getText().toString();
                final String optionAInput=((EditText)dialogView.findViewById(R.id.A_input)).getText().toString();
                final String optionBInput=((EditText)dialogView.findViewById(R.id.B_input)).getText().toString();
                final String optionCInput=((EditText)dialogView.findViewById(R.id.C_input)).getText().toString();
                final String optionDInput=((EditText)dialogView.findViewById(R.id.D_input)).getText().toString();
                final int keyOptionInput=((RadioGroup)dialogView.findViewById(R.id.key_input)).getCheckedRadioButtonId();
                final String reasonInput=((EditText)dialogView.findViewById(R.id.reason_input)).getText().toString();
                TextView errorQuestion=dialogView.findViewById(R.id.error_question);
                if(statementInput.isEmpty())
                {
                    errorQuestion.setText(R.string.empty_statement_error);
                }
                else if(findByStatement(statementInput)!=-1)
                {
                    errorQuestion.setText(R.string.duplicate_statement_error);
                }
                else if(optionAInput.isEmpty())
                {
                    errorQuestion.setText(R.string.empty_optionA_error);
                }
                else if(optionBInput.isEmpty())
                {
                    errorQuestion.setText(R.string.empty_optionB_error);
                }
                else if(optionCInput.isEmpty())
                {
                    errorQuestion.setText(R.string.empty_optionC_error);
                }
                else if(optionDInput.isEmpty())
                {
                    errorQuestion.setText(R.string.empty_optionD_error);
                }
                else
                {
                    ((TextView)findViewById(R.id.qmessage)).setText("");
                    errorQuestion.setText("");
                    QuestionInfo info=new QuestionInfo(subjectId,"",statementInput,optionAInput,optionBInput,optionCInput,optionDInput,reasonInput);
                    if(keyOptionInput==R.id.keyA_input)
                    {
                        info.setKey(info.getOption1());
                    }
                    else if(keyOptionInput==R.id.keyB_input)
                    {
                        info.setKey(info.getOption2());
                    }
                    else if(keyOptionInput==R.id.keyC_input)
                    {
                        info.setKey(info.getOption3());
                    }
                    else if(keyOptionInput==R.id.keyD_input)
                    {
                        info.setKey(info.getOption4());
                    }
                    db.addQuestion(info);
                    list=db.getQuestionsListOfSubject(subjectId);
                    adapter.data=list;
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(list.size()-1);
                    dialog.dismiss();
                }
            }
        });
    }
}