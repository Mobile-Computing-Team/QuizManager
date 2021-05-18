//This adapter is used to show mcq buttons on quiz page

package com.mcteam.quizmanager.adapterM023;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mcteam.quizmanager.R;

import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> mcqNo;
    ArrayList<String> solvedStatus;
    boolean[]flags;
    //
    LayoutInflater layoutInflater;

    public CustomAdapter(Context context, ArrayList<String> mcqNo, ArrayList<String> solvedStatus, boolean[] flags) {
        this.context = context;
        this.mcqNo = mcqNo;
        this.solvedStatus = solvedStatus;
        this.flags = flags;
    }

    @Override
    public int getCount() {
        return mcqNo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(layoutInflater==null)
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=layoutInflater.inflate(R.layout.list_item_m023,null);

        Button btn=convertView.findViewById(R.id.button_view);
        TextView textView = convertView.findViewById(R.id.text_view_solved);
        ImageButton imgBtn = convertView.findViewById(R.id.flag);
        btn.setText(mcqNo.get(position));
        textView.setText(solvedStatus.get(position));
        if (flags[position])
            imgBtn.setVisibility(View.VISIBLE);
        else
            imgBtn.setVisibility(View.INVISIBLE);
        return convertView;
    }
}