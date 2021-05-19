package com.mcteam.quizmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MathMainM046 extends AppCompatActivity
{
    String operator;
    int id;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_main_m046);
    }

    public void ShowDialogM046(View view)
    {
        operator=((Button)view).getText().toString();
        if(operator.equals("Random"))
            operator="R";
        AlertDialog.Builder builder=new AlertDialog.Builder(MathMainM046.this);
        builder.setTitle("Select Difficulty Level");
        LayoutInflater layoutInflater=this.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.level_dialogm046,null));
        alertDialog=builder.create();
        alertDialog.show();
    }
    public void MathQuiz(View view)
    {
        String lvl=((Button)view).getText().toString();
        if(lvl.equals("Low"))
            id=1;
        else if(lvl.equals("Medium"))
            id=2;
        else
            id=3;
        Intent intent=new Intent(MathMainM046.this,MathQuizM043.class);
        intent.putExtra("operator",operator);
        intent.putExtra("level",id);
        alertDialog.dismiss();
        startActivity(intent);
    }
}