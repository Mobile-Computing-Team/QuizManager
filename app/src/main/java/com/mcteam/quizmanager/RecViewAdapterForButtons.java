package com.mcteam.quizmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import com.mcteam.quizmanager.R;
//import static android.os.Build.VERSION_CODES.R;

public class RecViewAdapterForButtons extends RecyclerView.Adapter<RecViewAdapterForButtons.ViewHolder>{

    Context context;
    ArrayList<String> arrayList;

    public RecViewAdapterForButtons(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public RecViewAdapterForButtons.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_m023,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecViewAdapterForButtons.ViewHolder holder, int position) {
        String pos=arrayList.get(position);
        holder.editBtn.setText(pos);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //if you will not specify these access specifiers then, these are by-default public
        public Button editBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            editBtn=itemView.findViewById(R.id.edit_button);
            editBtn.setOnClickListener(this);
        }



        //@SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v)
        {
            Log.d("ClickedFromViewHolder","Clicked");
            if(v.getId()==editBtn.getId()) {
                //v.setBackground(context.getResources().getDrawable(R.drawable.red));
//                if(v.getDrawingCacheBackgroundColor()!=context.getResources().getColor(R.color.green)) {
//                    //v.setBackgroundColor(context.getResources().getColor(R.color.green));
//                    //v.invalidate();
//
//                }
//                else
//                {
//                    v.setBackgroundResource(R.drawable.red);
//                }
                GradientDrawable myGrad = (GradientDrawable) v.getBackground();
                myGrad.setStroke(20, context.getResources().getColor(R.color.colorMedium));
                myGrad.setColor(context.getResources().getColor(R.color.colorHigh));
            }
        }
    }
}