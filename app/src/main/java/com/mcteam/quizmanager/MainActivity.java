package com.mcteam.quizmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper db=new DBHelper(this);
        SQLiteDatabase database=db.getWritableDatabase();
        setContentView(R.layout.activity_main);
    }

    public void GotoMathFun(View view) {
        Intent intent=new Intent(this,MathMainM046.class);
        startActivity(intent);
    }


    public void GotoAttemptMode(View view) {
        Toast.makeText(this, "Function executed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AttemptModeM023.class);
        startActivity(intent);
    }

    public void GotoCreatorMode(View view) {
        Intent intent=new Intent(this,CreatorModeM004.class);
        startActivity(intent);
    }

}