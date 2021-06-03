package com.mcteam.quizmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new DBHelper(this);
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
        final Intent intent=new Intent(this,CreatorModeM004.class);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        final View dialogView;
        final EditText dialogEditText;
        if(db.isPasswordTableEmpty()) {
            dialogView = inflater.inflate(R.layout.change_title_dialog, null);
            dialogEditText = dialogView.findViewById(R.id.sectionTitle);
            dialogEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            dialogEditText.setHint("Enter Password");
            TextView inputLabel = dialogView.findViewById(R.id.input_label);
            inputLabel.setText("Password: ");
            final AlertDialog dialog = builder.setView(dialogView).setPositiveButton("Create", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setTitle("Please Enter Password You want to use for this Mode").create();
            dialog.show();
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newPassword = dialogEditText.getText().toString();
                    if(newPassword.isEmpty())
                    {
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                        ((TextView)dialogView.findViewById(R.id.error)).setText("Password Can't be Empty!");
                    }
                    else if(newPassword.length()<8 || newPassword.length()>=15)
                    {
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                        ((TextView)dialogView.findViewById(R.id.error)).setText("Password Length must be Between 8 and 15!");
                    }
                    else
                    {
                        db.addPassword(newPassword);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Password Created Successfully! Now try entering your creator mode using this password", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            dialogView = inflater.inflate(R.layout.change_title_dialog, null);
            dialogEditText = dialogView.findViewById(R.id.sectionTitle);
            dialogEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            dialogEditText.setHint("Enter Password");
            TextView inputLabel = dialogView.findViewById(R.id.input_label);
            inputLabel.setText("Password: ");
            final AlertDialog dialog = builder.setView(dialogView).setPositiveButton("Submit", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setTitle("Please Enter Password to Access this Mode").create();
            dialog.show();
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newPassword = dialogEditText.getText().toString();
                    if(db.isCreator(newPassword))
                    {
                        Toast.makeText(MainActivity.this, "Creator Mode Access Granted!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(intent);
                    }
                    else
                    {
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                        ((TextView)dialogView.findViewById(R.id.error)).setText("Invalid Password!");
                    }
                }
            });
        }
    }

}