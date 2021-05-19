package com.mcteam.quizmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuestionsManageM004 extends AppCompatActivity {
    RecyclerView recyclerView;
    QuestionViewListAdapter adapter;
    ArrayList<QuestionInfo> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_manage_m004);
        recyclerView=findViewById(R.id.question_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new QuestionViewListAdapter(this,list,(TextView)findViewById(R.id.qmessage));
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
                    errorQuestion.setText("Statement can't be empty");
                }
                else if(findByStatement(statementInput)!=-1)
                {
                    errorQuestion.setText("Duplicate Statement");
                }
                else if(optionAInput.isEmpty())
                {
                    errorQuestion.setText("Option A can't be empty");
                }
                else if(optionBInput.isEmpty())
                {
                    errorQuestion.setText("Option B can't be empty");
                }
                else if(optionCInput.isEmpty())
                {
                    errorQuestion.setText("Option C can't be empty");
                }
                else if(optionDInput.isEmpty())
                {
                    errorQuestion.setText("Option D can't be empty");
                }
                else
                {
                    ((TextView)findViewById(R.id.qmessage)).setText("");
                    errorQuestion.setText("");
                    list.add(0,new QuestionInfo(1,1,"",statementInput,optionAInput,optionBInput,optionCInput,optionDInput,reasonInput));
                    if(keyOptionInput==R.id.keyA_input)
                    {
                        list.get(0).setKey(list.get(0).getOption1());
                    }
                    else if(keyOptionInput==R.id.keyB_input)
                    {
                        list.get(0).setKey(list.get(0).getOption2());
                    }
                    else if(keyOptionInput==R.id.keyC_input)
                    {
                        list.get(0).setKey(list.get(0).getOption3());
                    }
                    else if(keyOptionInput==R.id.keyD_input)
                    {
                        list.get(0).setKey(list.get(0).getOption4());
                    }
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                    dialog.dismiss();
                }
            }
        });
    }
}