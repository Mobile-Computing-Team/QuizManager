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

    public QuestionViewListAdapter(Context context, ArrayList<QuestionInfo> data, TextView message) {
        this.data = data;
        this.context = context;
        this.message=message;
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
            LayoutInflater inflater=LayoutInflater.from(context);
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
                inputLabel.setText("Question Statement: ");
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
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Statement Can't be Empty!");
                        }
                        else if(findByStatement(newStatement)==-1) {
                            data.get(position).setStatement(dialogEditText.getText().toString());
                            notifyItemChanged(position);
                            dialog.dismiss();
                        }else{
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Duplicate Statement!");
                        }
                    }
                });
            }
            else if(view.getId()==delete.getId())
            {
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.remove(position);
                        notifyItemRemoved(position);
                        if(data.isEmpty()){
                            message.setText("No Questions Here! Try Adding Some Questions");
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
                inputLabel.setText("Option A: ");
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
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Option A Can't be Empty!");
                        }
                        else{
                            if(data.get(position).getOption1()==data.get(position).getKey())
                            {
                                data.get(position).setKey(newOptionA);
                            }
                            data.get(position).setOption1(newOptionA);
                            notifyItemChanged(position);
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
                inputLabel.setText("Option B: ");
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
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Option B Can't be Empty!");
                        }
                        else{
                            if(data.get(position).getOption2()==data.get(position).getKey())
                            {
                                data.get(position).setKey(newOptionB);
                            }
                            data.get(position).setOption2(newOptionB);
                            notifyItemChanged(position);
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
                inputLabel.setText("Option C: ");
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
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Option C Can't be Empty!");
                        }
                        else{
                            if(data.get(position).getOption3()==data.get(position).getKey())
                            {
                                data.get(position).setKey(newOptionC);
                            }
                            data.get(position).setOption3(newOptionC);
                            notifyItemChanged(position);
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
                inputLabel.setText("Option D: ");
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
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Option D Can't be Empty!");
                        }
                        else{
                            if(data.get(position).getOption4()==data.get(position).getKey())
                            {
                                data.get(position).setKey(newOptionD);
                            }
                            data.get(position).setOption4(newOptionD);
                            notifyItemChanged(position);
                            dialog.dismiss();
                        }
                    }
                });
            }
            else if(view.getId()==keyOption.getId())
            {
                dialogView=inflater.inflate(R.layout.change_key_option_dialog,null);
                String key=data.get(position).getKey();
                if(key.equals(data.get(position).getOption2()))
                {
                    ((RadioButton)dialogView.findViewById(R.id.change_keyA_input)).setChecked(false);
                    ((RadioButton)dialogView.findViewById(R.id.change_keyB_input)).setChecked(true);
                }
                else if(key.equals(data.get(position).getOption3()))
                {
                    ((RadioButton)dialogView.findViewById(R.id.change_keyA_input)).setChecked(false);
                    ((RadioButton)dialogView.findViewById(R.id.change_keyC_input)).setChecked(true);
                }
                else if(key.equals(data.get(position).getOption4()))
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
                            data.get(position).setKey(data.get(position).getOption1());
                        }
                        else if(keyChangeInput==R.id.change_keyB_input)
                        {
                            data.get(position).setKey(data.get(position).getOption2());
                        }
                        else if(keyChangeInput==R.id.change_keyC_input)
                        {
                            data.get(position).setKey(data.get(position).getOption3());
                        }
                        else if(keyChangeInput==R.id.change_keyD_input)
                        {
                            data.get(position).setKey(data.get(position).getOption4());
                        }
                        notifyItemChanged(position);
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
                inputLabel.setText("Reason: ");
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
                        data.get(position).setReason(newReason);
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });
            }
        }
    }
}
