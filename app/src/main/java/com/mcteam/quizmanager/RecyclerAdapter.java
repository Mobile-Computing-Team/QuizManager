package com.mcteam.quizmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<SubjectInfo> data;
    Context context;
    EditText dialogEditText;
    AlertDialog.Builder builder;
    LayoutInflater inflater;
    View dialogView;

    public RecyclerAdapter(Context context,ArrayList<SubjectInfo> data) {
        this.data = data;
        this.context = context;
        inflater=LayoutInflater.from(context);
        builder=new AlertDialog.Builder(context);
    }


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.qmcll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialogView=inflater.inflate(R.layout.change_mcq_dialog,null);
                builder.setView(dialogView).setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.get(position).quizMCQs=Integer.parseInt(((EditText)dialogView.findViewById(R.id.quiz_mcqs)).getText().toString());
                        notifyItemChanged(position);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setTitle("Change Quiz MCQs").create().show();
            }
        });
        holder.qdll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialogView=inflater.inflate(R.layout.change_duration_dialog,null);
                builder.setView(dialogView).setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.get(position).hours=Integer.parseInt(((EditText)dialogView.findViewById(R.id.quiz_duration_hour)).getText().toString());
                        data.get(position).minutes=Integer.parseInt(((EditText)dialogView.findViewById(R.id.quiz_duration_minute)).getText().toString());
                        data.get(position).seconds=Integer.parseInt(((EditText)dialogView.findViewById(R.id.quiz_duration_second)).getText().toString());
                        notifyItemChanged(position);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setTitle("Change Quiz Duration").create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView questionsCount;
        TextView quizMCQsCount;
        TextView quizDuration;
        LinearLayout qmcll;
        LinearLayout qdll;
        ImageButton edit;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            questionsCount=itemView.findViewById(R.id.total_questions);
            quizMCQsCount=itemView.findViewById(R.id.quiz_mcqs);
            quizDuration=itemView.findViewById(R.id.quiz_duration);
            qmcll=itemView.findViewById(R.id.qmll);
            qdll=itemView.findViewById(R.id.qdll);
            edit=itemView.findViewById(R.id.edit_button);
            delete=itemView.findViewById(R.id.delete_button);
        }
        int findByTitle(String title)
        {
            for(int i=0;i<data.size();i++)
            {
                if(data.get(i).title==title)
                {
                    return i;
                }
            }
            return -1;
        }
        @Override
        public void onClick(View view) {
            if(view.getId()==edit.getId())
            {
                dialogView=inflater.inflate(R.layout.change_title_dialog,null);
                builder.setView(dialogView).setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((EditText)dialogView.findViewById(R.id.sectionTitle)).getText().toString();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setTitle("Change Subject Title").create().show();
            }
            else if(view.getId()==delete.getId())
            {
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int ind=findByTitle(title.getText().toString());
                        data.remove(ind);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //dialogInterface.cancel();
                    }
                }).setTitle("Confirm?").setMessage("Do you really want to remove this subject?").create().show();
            }
        }
    }
}
