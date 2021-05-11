package com.mcteam.quizmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MathQuizM043 extends AppCompatActivity {

    EditText editText;
    TextView viewNum1,viewNum2,viewOptr;
    Button submitBtn;
    int  sub,div,max,min,randomNum1,randomNum2;
    long sum,mul,ans;
    Random rn;
    String op,op2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz_m043);

        viewNum1=findViewById(R.id.tvNum1);
        viewOptr=findViewById(R.id.tvOptr);
        viewNum2=findViewById(R.id.tvNum2);
        editText=findViewById(R.id.userInput);
        submitBtn=findViewById(R.id.submitBtn);


        Bundle extra=getIntent().getExtras();

        op=op2=extra.getString("operator");
        int levelNo=extra.getInt("level");

        if(levelNo==1){

            min=0;
            max=9;
        }
        else if(levelNo==2){
            min=10;
            max=99;
        }
        else if(levelNo==3){
            min=100;
            max=999;
        }
        display();

    }

    private void display(){

        if(op2.equals("r")||op2.equals("R")){
            rn=new Random();
            int randomNum=rn.nextInt(4)+1;
            if(randomNum==1){
                op="+";
            }
            else if(randomNum==2){
                op="-";
            }
            else if(randomNum==3){
                op="×";
            }
            else if(randomNum==4){

                op="÷";
            }
        }
        randomNum1=generate_random_num();
        if(op.equals("÷")&& randomNum1==0){
            randomNum1++;
        }
        randomNum2=generate_random_num();

        viewNum1.setText(String.valueOf(randomNum2));

        viewOptr.setText(op);

        viewNum2.setText(String.valueOf(randomNum1));

        editText=findViewById(R.id.userInput);
        editText.setText("");


        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String yourAns=editText.getText().toString();
                if(!yourAns.equals("")){
                    ans=Integer.parseInt(yourAns);

                    switch (op) {
                        case "+":

                            sum=randomNum1+randomNum2;
                            show_answer(op,ans,sum);
                            break;
                        case "-":

                            sub=randomNum2-randomNum1;
                            show_answer(op,ans,sub);
                            break;
                        case "×":

                            mul=randomNum1*randomNum2;
                            show_answer(op,ans,mul);
                            break;
                        case "÷":

                            div=randomNum2/randomNum1;
                            show_answer(op,ans,div);
                            break;


                    }


                }
                else{
                    //display();
                }


            }
        });


    }

    private int generate_random_num() {
        rn=new Random();
        int range=max-min+1;
        return  (rn.nextInt(range)+min);
    }
    private void show_answer(final String op, long yourAns, long correctAns) {

        AlertDialog.Builder alert=new AlertDialog.Builder(MathQuizM043.this);
        alert.setCancelable(false);
        if(yourAns==correctAns){
            alert.setTitle("Your answer is Correct!");
            alert.setIcon(R.drawable.correct);
        }
        if(yourAns!=correctAns){
            alert.setTitle("Your answer is Wrong");
            alert.setMessage("Correct answer is  "+correctAns+"\n");
            alert.setIcon(R.drawable.wrong);
        }

        alert.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(MathQuizM043.this,MathMainM046.class);
                startActivity(intent);

            }
        });
        alert.setNegativeButton("Do Another One", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                display();

            }
        });
        alert.show();
    }
}