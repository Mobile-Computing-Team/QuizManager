package com.mcteam.quizmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GotoMathFun(View view) {
        //TODO: BSEF18M046! Uncomment Following Code and Create a new activity named MathMain
        /*Intent intent=new Intent(this,MathMain.class);
        startActivity(intent);*/
    }
}