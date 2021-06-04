package com.mcteam.quizmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionViewListAdapter extends RecyclerView.Adapter<QuestionViewListAdapter.ViewHolder> {
    ArrayList<QuestionInfo> data;
    Context context;
    TextView message;
    DBHelper db;
    int subjectId;

    public QuestionViewListAdapter(Context context, ArrayList<QuestionInfo> data, TextView message,DBHelper db,int subjectId) {
        this.data = data;
        this.context = context;
        this.message=message;
        this.db=db;
        this.subjectId=subjectId;
    }

    @NonNull
    @Override
    public QuestionViewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.question_item_m004,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewListAdapter.ViewHolder holder, int position) {
        holder.statement.setText(data.get(position).getStatement());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View main;
        TextView statement;
        ImageButton edit;
        ImageButton delete;
        TextView optionA;
        TextView optionB;
        TextView optionC;
        TextView optionD;
        TextView keyOption;
        TextView reason;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main=itemView;
            statement=itemView.findViewById(R.id.statement);
            edit=itemView.findViewById(R.id.question_edit_button);
            delete=itemView.findViewById(R.id.question_delete_button);
            optionA=itemView.findViewById(R.id.A);
            optionB=itemView.findViewById(R.id.B);
            optionC=itemView.findViewById(R.id.C);
            optionD=itemView.findViewById(R.id.D);
            keyOption=itemView.findViewById(R.id.Key);
            reason=itemView.findViewById(R.id.Reason);
            main.setOnClickListener(this);
            main.setOnClickListener(this);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
            optionA.setOnClickListener(this);
            optionB.setOnClickListener(this);
            optionC.setOnClickListener(this);
            optionD.setOnClickListener(this);
            keyOption.setOnClickListener(this);
            reason.setOnClickListener(this);
            statement.setHorizontallyScrolling(true);
        }
        int findByStatement(String statement)
        {
            for(int i=0;i<data.size();i++)
            {
                if(data.get(i).getStatement().equals(statement))
                {
                    return i;
                }
            }
            return -1;
        }

        @SuppressLint("InflateParams")
        @Override
        public void onClick(final View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            final LayoutInflater inflater=LayoutInflater.from(context);
            final View dialogView;
            final EditText dialogEditText;
            position=findByStatement(statement.getText().toString());
            if(view.getId()==main.getId())
            {
                dialogView=inflater.inflate(R.layout.question_details_dialog,null);
                ((TextView)dialogView.findViewById(R.id.detail_statement)).setText(data.get(position).getStatement());
                ((TextView)dialogView.findViewById(R.id.detail_optionA)).setText(data.get(position).getOption1());
                ((TextView)dialogView.findViewById(R.id.detail_optionB)).setText(data.get(position).getOption2());
                ((TextView)dialogView.findViewById(R.id.detail_optionC)).setText(data.get(position).getOption3());
                ((TextView)dialogView.findViewById(R.id.detail_optionD)).setText(data.get(position).getOption4());
                ((TextView)dialogView.findViewById(R.id.detail_key_option)).setText(data.get(position).getKey());
                ((TextView)dialogView.findViewById(R.id.detail_reason)).setText(data.get(position).getReason());
                builder.setTitle("Question Details").setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setView(dialogView).create().show();
            }
            else if(view.getId()==edit.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setHint("Enter Question Statement");
                dialogEditText.setText(data.get(position).getStatement());
                TextView inputLabel=dialogView.findViewById(R.id.input_label);
                inputLabel.setText(R.string.statement_label);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Question Statement").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newStatement=dialogEditText.getText().toString();
                        if(newStatement.isEmpty())
                        {
                            dialogView.findViewById(R.id.error).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_statement_error);
                        }
                        else if(findByStatement(newStatement)==-1) {
                            QuestionInfo info=data.get(position);
                            info.setStatement(newStatement);
                            db.updateQuestion(info.id,info);
                            data=db.getQuestionsListOfSubject(subjectId);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }else{
                            dialogView.findViewById(R.id.error).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.duplicate_statement_error);
                        }
                    }
                });
            }
            else if(view.getId()==delete.getId())
            {
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.removeQuestion(data.get(position).id);
                        data=db.getQuestionsListOfSubject(subjectId);
                        notifyDataSetChanged();
                        if(data.isEmpty()){
                            message.setText(R.string.qmessage);
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Confirm?").setMessage("Do you really want to remove this question?").create().show();
            }
            else if(view.getId()==optionA.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setHint("Enter Option A");
                dialogEditText.setText(data.get(position).getOption1());
                TextView inputLabel=dialogView.findViewById(R.id.input_label);
                inputLabel.setText(R.string.optionA_label);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Option A").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newOptionA=dialogEditText.getText().toString();
                        if(newOptionA.isEmpty())
                        {
                            dialogView.findViewById(R.id.error).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_optionA_error);
                        }
                        else{
                            QuestionInfo info=data.get(position);
                            if(info.getOption1().equals(info.getKey()))
                            {
                                info.setKey(newOptionA);
                            }
                            info.setOption1(newOptionA);
                            db.updateQuestion(info.id,info);
                            data=db.getQuestionsListOfSubject(subjectId);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
            else if(view.getId()==optionB.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setHint("Enter Option B");
                dialogEditText.setText(data.get(position).getOption2());
                TextView inputLabel=dialogView.findViewById(R.id.input_label);
                inputLabel.setText(R.string.optionB_label);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Option B").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newOptionB=dialogEditText.getText().toString();
                        if(newOptionB.isEmpty())
                        {
                            dialogView.findViewById(R.id.error).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_optionB_error);
                        }
                        else{
                            QuestionInfo info=data.get(position);
                            if(info.getOption2().equals(info.getKey()))
                            {
                                info.setKey(newOptionB);
                            }
                            info.setOption2(newOptionB);
                            db.updateQuestion(info.id,info);
                            data=db.getQuestionsListOfSubject(subjectId);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
            else if(view.getId()==optionC.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setHint("Enter Option C");
                dialogEditText.setText(data.get(position).getOption3());
                TextView inputLabel=dialogView.findViewById(R.id.input_label);
                inputLabel.setText(R.string.optionC_label);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Option C").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newOptionC=dialogEditText.getText().toString();
                        if(newOptionC.isEmpty())
                        {
                            dialogView.findViewById(R.id.error).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_optionC_error);
                        }
                        else{
                            QuestionInfo info=data.get(position);
                            if(info.getOption3().equals(info.getKey()))
                            {
                                info.setKey(newOptionC);
                            }
                            info.setOption3(newOptionC);
                            db.updateQuestion(info.id,info);
                            data=db.getQuestionsListOfSubject(subjectId);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
            else if(view.getId()==optionD.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setHint("Enter Option D");
                dialogEditText.setText(data.get(position).getOption4());
                TextView inputLabel=dialogView.findViewById(R.id.input_label);
                inputLabel.setText(R.string.optionD_label);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Option D").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newOptionD=dialogEditText.getText().toString();
                        if(newOptionD.isEmpty())
                        {
                            dialogView.findViewById(R.id.error).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText(R.string.empty_optionD_error);
                        }
                        else{
                            QuestionInfo info=data.get(position);
                            if(info.getOption4().equals(info.getKey()))
                            {
                                info.setKey(newOptionD);
                            }
                            info.setOption4(newOptionD);
                            db.updateQuestion(info.id,info);
                            data=db.getQuestionsListOfSubject(subjectId);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
            else if(view.getId()==keyOption.getId())
            {
                dialogView=inflater.inflate(R.layout.change_key_option_dialog,null);
                final QuestionInfo info=data.get(position);
                String key=info.getKey();
                if(key.equals(info.getOption2()))
                {
                    ((RadioButton)dialogView.findViewById(R.id.change_keyA_input)).setChecked(false);
                    ((RadioButton)dialogView.findViewById(R.id.change_keyB_input)).setChecked(true);
                }
                else if(key.equals(info.getOption3()))
                {
                    ((RadioButton)dialogView.findViewById(R.id.change_keyA_input)).setChecked(false);
                    ((RadioButton)dialogView.findViewById(R.id.change_keyC_input)).setChecked(true);
                }
                else if(key.equals(info.getOption4()))
                {
                    ((RadioButton)dialogView.findViewById(R.id.change_keyA_input)).setChecked(false);
                    ((RadioButton)dialogView.findViewById(R.id.change_keyD_input)).setChecked(true);
                }
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Key Option").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int keyChangeInput=((RadioGroup)dialogView.findViewById(R.id.change_key_input)).getCheckedRadioButtonId();
                        if(keyChangeInput==R.id.change_keyA_input)
                        {
                            info.setKey(info.getOption1());
                        }
                        else if(keyChangeInput==R.id.change_keyB_input)
                        {
                            info.setKey(info.getOption2());
                        }
                        else if(keyChangeInput==R.id.change_keyC_input)
                        {
                            info.setKey(info.getOption3());
                        }
                        else if(keyChangeInput==R.id.change_keyD_input)
                        {
                            info.setKey(info.getOption4());
                        }
                        db.updateQuestion(info.id,info);
                        data=db.getQuestionsListOfSubject(subjectId);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
            else if(view.getId()==reason.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setHint("Enter Reason");
                if(!data.get(position).getReason().equals("None"))
                {
                    dialogEditText.setText(data.get(position).getReason());
                }
                TextView inputLabel=dialogView.findViewById(R.id.input_label);
                inputLabel.setText(R.string.reason_label);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Reason").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newReason=dialogEditText.getText().toString();
                        QuestionInfo info=data.get(position);
                        info.setReason(newReason);
                        db.updateQuestion(info.id,info);
                        data=db.getQuestionsListOfSubject(subjectId);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        }
    }
}
