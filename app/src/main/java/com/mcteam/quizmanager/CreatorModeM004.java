package com.mcteam.quizmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreatorModeM004 extends AppCompatActivity {
    RecyclerView recyclerView;
    SubjectViewListAdapter adapter;
    ArrayList<SubjectInfo> list;
    DBHelper db;
    static boolean hasComeFromQuestions=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_mode_m004);
        db=new DBHelper(this);
        list=db.getSubjectsList();
        if(list.size()!=0)
        {
            ((TextView)findViewById(R.id.message)).setText("");
        }
        else
        {
            ((TextView)findViewById(R.id.message)).setText(R.string.empty_message);
        }
        recyclerView=findViewById(R.id.section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SubjectViewListAdapter(this,list,(TextView) findViewById(R.id.message),db);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hasComeFromQuestions)
        {
            list=db.getSubjectsList();
            adapter.data=list;
            adapter.notifyDataSetChanged();
        }
    }

    int findByTitle(String title)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).title.equals(title))
            {
                return i;
            }
        }
        return -1;
    }

    public void AddSubject(View view) {
        final View dialogView=getLayoutInflater().inflate(R.layout.change_title_dialog,null);
        final EditText dialogEditText=dialogView.findViewById(R.id.sectionTitle);
        final AlertDialog dialog=new AlertDialog.Builder(this).setView(dialogView).setPositiveButton("Create",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setTitle("Create New Subject").create();
        dialog.show();
        Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String newTitle=dialogEditText.getText().toString();
                    if(newTitle.isEmpty())
                    {
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                        ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_title_error);
                    }
                    else if(findByTitle(newTitle)==-1) {
                        ((TextView)findViewById(R.id.message)).setText("");
                        db.addSubject(new SubjectInfo(newTitle));
                        list=db.getSubjectsList();
                        adapter.data=list;
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(list.size()-1);
                        dialog.dismiss();
                    }else{
                        ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                        ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.duplicate_title_error);
                    }
            }
        });
    }

    public void ChangePassword() {
        final View dialogView=getLayoutInflater().inflate(R.layout.change_title_dialog,null);
        final EditText dialogEditText=dialogView.findViewById(R.id.sectionTitle);
        dialogEditText.setHint("Enter New Password");
        dialogEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView inputLabel=dialogView.findViewById(R.id.input_label);
        inputLabel.setText(R.string.change_password_label);
        final AlertDialog dialog=new AlertDialog.Builder(this).setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setTitle("Change Password").create();
        dialog.show();
        Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = dialogEditText.getText().toString();
                if(newPassword.isEmpty())
                {
                    ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                    ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_password_error);
                }
                else if(newPassword.length()<8 || newPassword.length()>=15)
                {
                    ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                    ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.password_length_error);
                }
                else
                {
                    db.updatePassword(newPassword);
                    dialog.dismiss();
                    Toast.makeText(CreatorModeM004.this, "Password Updated Successfully! Now Access this Mode Using New Password", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    public void VerifyIdentity(View view) {
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        final View dialogView;
        final EditText dialogEditText;
        dialogView = inflater.inflate(R.layout.change_title_dialog, null);
        dialogEditText = dialogView.findViewById(R.id.sectionTitle);
        dialogEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dialogEditText.setHint("Enter Your Old Password");
        TextView inputLabel = dialogView.findViewById(R.id.input_label);
        inputLabel.setText(R.string.old_password_label);
        final android.app.AlertDialog dialog = builder.setView(dialogView).setPositiveButton("Submit", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setTitle("We need to verify your Identity").create();
        dialog.show();
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = dialogEditText.getText().toString();
                if(db.isCreator(newPassword))
                {
                    Toast.makeText(CreatorModeM004.this, "Identity Verified!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    ChangePassword();
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