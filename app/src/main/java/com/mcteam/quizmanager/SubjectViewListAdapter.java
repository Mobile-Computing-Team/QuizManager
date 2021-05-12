package com.mcteam.quizmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectViewListAdapter extends RecyclerView.Adapter<SubjectViewListAdapter.ViewHolder> {
    ArrayList<SubjectInfo> data;
    Context context;
    TextView message;

    public SubjectViewListAdapter(Context context, ArrayList<SubjectInfo> data, TextView message) {
        this.data = data;
        this.context = context;
        this.message=message;
    }

    @NonNull
    @Override
    public SubjectViewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.section_item_m004,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(data.get(position).title);
        holder.questionsCount.setText(String.valueOf(data.get(position).totalQuestions));
        holder.quizMCQsCount.setText(String.valueOf(data.get(position).quizMCQs));
        holder.quizDuration.setText(String.format("%02d:%02d:%02d",data.get(position).hours,data.get(position).minutes,data.get(position).seconds));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        View main;
        TextView title;
        TextView questionsCount;
        TextView quizMCQsCount;
        TextView quizDuration;
        LinearLayout qmcll;
        LinearLayout qdll;
        ImageButton edit;
        ImageButton delete;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main=itemView;
            title=itemView.findViewById(R.id.title);
            questionsCount=itemView.findViewById(R.id.total_questions);
            quizMCQsCount=itemView.findViewById(R.id.quiz_mcqs);
            quizDuration=itemView.findViewById(R.id.quiz_duration);
            qmcll=itemView.findViewById(R.id.qmll);
            qdll=itemView.findViewById(R.id.qdll);
            edit=itemView.findViewById(R.id.edit_button);
            delete=itemView.findViewById(R.id.delete_button);
            main.setOnClickListener(this);
            delete.setOnClickListener(this);
            edit.setOnClickListener(this);
            qmcll.setOnTouchListener(this);
            qdll.setOnTouchListener(this);
        }
        int findByTitle(String title)
        {
            for(int i=0;i<data.size();i++)
            {
                if(data.get(i).title.equals(title))
                {
                    return i;
                }
            }
            return -1;
        }
        @Override
        public void onClick(final View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            LayoutInflater inflater=LayoutInflater.from(context);
            final View dialogView;
            final EditText dialogEditText;
            position=findByTitle(title.getText().toString());
            if(view.getId()==main.getId())
            {
                Intent intent=new Intent(context,QuestionsManageM004.class);
                context.startActivity(intent);
            }
            else if(view.getId()==edit.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                dialogEditText=dialogView.findViewById(R.id.sectionTitle);
                dialogEditText.setText(data.get(position).title);
                final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Change Subject Title").create();
                dialog.show();
                Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newTitle=dialogEditText.getText().toString();
                        if(newTitle.isEmpty())
                        {
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Title Can't be Empty!");
                        }
                        else if(findByTitle(newTitle)==-1) {
                            data.get(position).title=dialogEditText.getText().toString();
                            notifyItemChanged(position);
                            dialog.dismiss();
                        }else{
                            ((TextView)dialogView.findViewById(R.id.error)).setVisibility(View.VISIBLE);
                            ((TextView)dialogView.findViewById(R.id.error)).setText("Duplicate Title!");
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
                            message.setText("No Subjects Here! Try Adding Some Subjects");
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setTitle("Confirm?").setMessage("Do you really want to remove this subject?").create().show();
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction()==MotionEvent.ACTION_UP)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater=LayoutInflater.from(context);
                final View dialogView;
                final EditText dialogEditText;
                position=findByTitle(title.getText().toString());
                if(view.getId()==qmcll.getId())
                {
                    dialogView=inflater.inflate(R.layout.change_mcq_dialog,null);
                    dialogEditText=dialogView.findViewById(R.id.quiz_mcqs);
                    dialogEditText.setText(String.valueOf(data.get(position).quizMCQs));
                    final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change", null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setTitle("Change Quiz MCQs").create();
                    dialog.show();
                    Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String input=dialogEditText.getText().toString();
                            if(input.isEmpty())
                            {
                                ((TextView)dialogView.findViewById(R.id.error_mcq)).setVisibility(View.VISIBLE);
                                ((TextView)dialogView.findViewById(R.id.error_mcq)).setText("No. of MCQs Can't be Empty!");
                            }
                            else{
                            data.get(position).quizMCQs=Integer.parseInt(dialogEditText.getText().toString());
                            notifyItemChanged(position);
                            dialog.dismiss();
                            }
                        }
                    });
                }
                else if(view.getId()==qdll.getId())
                {
                    dialogView=inflater.inflate(R.layout.change_duration_dialog,null);
                    final EditText hoursInput=((EditText) dialogView.findViewById(R.id.quiz_duration_hour));
                    final EditText minutesInput=((EditText) dialogView.findViewById(R.id.quiz_duration_minute));
                    final EditText secondsInput=((EditText) dialogView.findViewById(R.id.quiz_duration_second));
                    hoursInput.setText(String.format("%02d",data.get(position).hours));
                    minutesInput.setText(String.format("%02d",data.get(position).minutes));
                    secondsInput.setText(String.format("%02d",data.get(position).seconds));
                    final AlertDialog dialog= builder.setView(dialogView).setPositiveButton("Change",null).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setTitle("Change Quiz Duration").create();
                    dialog.show();
                    Button positiveButton=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String hours=hoursInput.getText().toString();
                            String minutes=minutesInput.getText().toString();
                            String seconds=secondsInput.getText().toString();
                            if(hours.isEmpty() || minutes.isEmpty() || seconds.isEmpty() || Integer.parseInt(hours)>23 ||Integer.parseInt(minutes)>59 ||Integer.parseInt(minutes)>59)
                            {
                                ((TextView)dialogView.findViewById(R.id.error_duration)).setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                data.get(position).hours = Integer.parseInt(((EditText) dialogView.findViewById(R.id.quiz_duration_hour)).getText().toString());
                                data.get(position).minutes = Integer.parseInt(((EditText) dialogView.findViewById(R.id.quiz_duration_minute)).getText().toString());
                                data.get(position).seconds = Integer.parseInt(((EditText) dialogView.findViewById(R.id.quiz_duration_second)).getText().toString());
                                notifyItemChanged(position);
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
            return false;
        }
    }
}
