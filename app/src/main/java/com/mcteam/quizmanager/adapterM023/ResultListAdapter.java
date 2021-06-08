//This adapter is used to show questions list as a correct and wrong

package com.mcteam.quizmanager.adapterM023;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcteam.quizmanager.QuestionInfo;
import com.mcteam.quizmanager.R;
import com.mcteam.quizmanager.StartQuizM023;

import java.util.ArrayList;


public class ResultListAdapter extends BaseAdapter {
    Context context;
    ArrayList<QuestionInfo> quizQuestions;
    int[]userAnswers;
    LayoutInflater layoutInflater;

    public ResultListAdapter(Context context, ArrayList<QuestionInfo> quizQuestions, int[] userAnswers) {
        this.context = context;
        this.quizQuestions = quizQuestions;
        this.userAnswers = userAnswers;
    }

    @Override
    public int getCount() {
        return quizQuestions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater==null)
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=layoutInflater.inflate(R.layout.result_item_m023,null);

        TextView txt=convertView.findViewById(R.id.result_Item_Text_View);
        LinearLayout layout= (LinearLayout) convertView.findViewById(R.id.result_item);
        ImageButton imgBtn=convertView.findViewById(R.id.question_status);
        txt.setText("Question # "+String.valueOf(position+1));
        boolean quesStatus=(StartQuizM023.getAnswer(quizQuestions.get(position)) ==userAnswers[position]);

        if(quesStatus)
            layout.setBackgroundColor(context.getResources().getColor(R.color.colorLow));
        else
            layout.setBackgroundColor(context.getResources().getColor(R.color.red));
        imgBtn.setImageResource(quesStatus?R.drawable.correct_answer:R.drawable.wrong_answer);
        return convertView;
    }
}
