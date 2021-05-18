package com.mcteam.quizmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mcteam.quizmanager.adapterM023.BundleM023;
import com.mcteam.quizmanager.adapterM023.CustomAdapter;
import com.mcteam.quizmanager.adapterM023.RecViewAdpaterForSub;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StartQuizM023 extends AppCompatActivity {

    public static int currQues;
    public static int preQues;
    public static int[]userAnswers;
    public static boolean[]flagQues;
    public static ArrayList<String> solved;
    ArrayList<String> mcqNo; //it will passed to adapter
    long tempSec;            //temporary variable for time storing which will also help out in bundle saving on screen rotation
    int hour;
    int min;
    int sec;
    CountDownTimer timerSave;

    static RadioGroup radioGroup;
    static TextView questionTitle;
    static RadioButton option1;
    static RadioButton option2;
    static RadioButton option3;
    static RadioButton option4;
    static TextView timeView;
    boolean finishFlag;       //it will used to indicate finished time
    boolean submitFlag;       //it will used to indicate that we are in submit function

    //static RecyclerView recyclerView;
    //RecViewButtonAdapter recyclerViewAdapter;
    CustomAdapter adapter;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz_m023);
        //initializing Views
        initializeViews();


        userAnswers=new int[RecViewAdpaterForSub.mcqCount];   //will keep answers of users
        flagQues=new boolean[RecViewAdpaterForSub.mcqCount];  //will keep record of flaged ques
        solved=new ArrayList<>();                             //will keep string N/A or solved
        finishFlag=false;
        submitFlag=false;
        currQues=0;
        mcqNo=new ArrayList<>();
        hour=getIntent().getExtras().getInt("hour");
        min=getIntent().getExtras().getInt("min");
        sec=getIntent().getExtras().getInt("sec");
        for(int i=0;i<RecViewAdpaterForSub.mcqCount;i++) {
            userAnswers[i] = 0;              //no answer saved yet
            mcqNo.add(String.valueOf(i+1));  //mcq#
            flagQues[i]=false;  //ques is not flaged yet
            solved.add("N/A"); //means no answer saved yet
        }


        if(savedInstanceState!=null)
        {
            BundleM023 data=DBHelper.getBundle();
            currQues=data.getCurrQues();
            userAnswers=data.getUserAnswers();
            flagQues=data.getFlagQues();
            solved=data.getSolved();
            finishFlag=data.isFinishFlag();
            submitFlag=data.isSubmitFlag();
            hour=data.getHour();
            min=data.getMin();
            sec=data.getSec();
            if(finishFlag)//means time was over when user rotate screen
            {
                //navigate to result
                closeQuiz();
            }

            else if(submitFlag)
            {
                //open dialouge
                submit(null);
            }

            //how to manage time :(
        }
        generateQuestion(currQues);

        //recyclerView initialization


        ////recyclerView=findViewById(R.id.recyclerViewForButtons);
        ////recyclerView.setHasFixedSize(true);
        ////recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        ////recyclerViewAdapter=new RecViewButtonAdapter(RecViewAdpaterForSub.context,mcqNo);
        ////recyclerView.setAdapter(recyclerViewAdapter);

        //setting counter
        timerSave=new CountDownTimer(hour*3600000+min*60000+sec*1000,1000) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTick(long millisUntilFinished) {  //it will be called at each countDownIntervals after starting
                //timeView.setText(String.valueOf(millisUntilFinished/1000));
                //counter++;
                //timeView.setText(String.valueOf(counter));
                tempSec=millisUntilFinished/1000;
                if(tempSec<60)
                    timeView.setTextColor(getResources().getColor(R.color.colorHigh));
                timeView.setText(String.format("%02dh %02dm %02ds",(tempSec/60)/60,( tempSec/ 60 ) % 60 , tempSec%60));
            }
            @Override
            public void onFinish() {
                //hourView.setText(String.valueOf(0));
                //same work as of submit button
                finishFlag=true;
                if(submitFlag==false)
                   closeQuiz();
                //else manage it in submit func that time is up, and close quiz
            }
        }.start();


        listView=findViewById(R.id.listViewForButtons);
        adapter=new CustomAdapter(StartQuizM023.this,mcqNo,solved,flagQues);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button mcqNoBtn=(Button) view.findViewById(R.id.button_view);                 //having mcq#
                TextView solvedStatusText=(TextView) view.findViewById(R.id.text_view_solved);  //having ques status
                ImageButton flag=(ImageButton) view.findViewById(R.id.flag);   //flag

                currQues=Integer.parseInt(String.valueOf(mcqNoBtn.getText()))-1;   //now current ques is that which is clicked by user from listView
                generateQuestion(currQues);                          //now generate ques
                Toast.makeText(StartQuizM023.this, String.valueOf(currQues), Toast.LENGTH_SHORT).show();
            }
        });

        //Also take care about screen rotation
    }

    public void initializeViews()
    {
        timeView=findViewById(R.id.timeTextView);
        questionTitle=findViewById(R.id.quesTitle);
        radioGroup=findViewById(R.id.radioGroup);
        option1=findViewById(R.id.radioButton);
        option2=findViewById(R.id.radioButton2);
        option3=findViewById(R.id.radioButton3);
        option4=findViewById(R.id.radioButton4);
    }
    public void radioClicked(View view) {
        radioGroup=findViewById(R.id.radioGroup);
        if(radioGroup.getCheckedRadioButtonId()==R.id.radioButton) //first option
            userAnswers[currQues]=1; //user saved option1
        else if(radioGroup.getCheckedRadioButtonId()==R.id.radioButton2) //2nd option
            userAnswers[currQues]=2;
        else if(radioGroup.getCheckedRadioButtonId()==R.id.radioButton3) //3rd option
            userAnswers[currQues]=3;
        else  //4th option
            userAnswers[currQues]=4;

        solved.set(currQues,"solved");     //user clicked answer
        adapter.notifyDataSetChanged();    //now update listView too
        //send currQues to preprocessing to make currbutton inner as white
    }

    public void clearChoice(View view) {
        userAnswers[currQues]=0;  //user clear option
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        solved.set(currQues,"N/A");       //means user not solved this ques, so update list too
        adapter.notifyDataSetChanged();   //it will update listView
        //send currQues to preprocessing to make this button inner as white
    }

    @SuppressLint("ResourceAsColor")
    public static void generateQuestion(int quesNo)
    {
        if(preQues!=-1)
        {
            //View v=recyclerView.getLayoutManager().findViewByPosition(preQues);
            //GradientDrawable myGrad=(GradientDrawable)v.getBackground();
            //myGrad.setStroke(20,R.color.white);
        }
        //View v=recyclerView.getLayoutManager().findViewByPosition(currQues+1);
        //GradientDrawable myGrad=(GradientDrawable)v.getBackground();
        //myGrad.setStroke(20,getResources().getColor(R.color.colorDivide));

        //send currQues to preprocessing to make button outer as blue
        //initializeViews();
        questionTitle.setText(RecViewAdpaterForSub.quizQuestions.get(quesNo).getStatement());
        option1.setText(RecViewAdpaterForSub.quizQuestions.get(quesNo).getOption1());
        option2.setText(RecViewAdpaterForSub.quizQuestions.get(quesNo).getOption2());
        option3.setText(RecViewAdpaterForSub.quizQuestions.get(quesNo).getOption3());
        option4.setText(RecViewAdpaterForSub.quizQuestions.get(quesNo).getOption4());

        radioGroup.clearCheck();

        if(userAnswers[quesNo]==1)
            option1.setChecked(true);
        else if(userAnswers[quesNo]==2)
            option2.setChecked(true);
        else if(userAnswers[quesNo]==3)
            option3.setChecked(true);
        else if(userAnswers[quesNo]==4)
            option4.setChecked(true);


        preQues=currQues;
    }

    public void backClicked(View view) {
        //preprocessing(currQues); to change outer color to white
        currQues--;
        if(currQues<0)
            currQues=RecViewAdpaterForSub.mcqCount-1; //circulate to back
        generateQuestion(currQues);
    }

    public void nextClicked(View view) {
        //preprocessing(currQues); to change outer color to white
        currQues=(currQues+1)%RecViewAdpaterForSub.mcqCount; //circulate to zero, if last mcq
        generateQuestion(currQues);
    }

    public void flagThisQues(View view) {
        //flag the currMcq, means make its inner red
        //if(flagQues[currQues]) //means ques was prev flaged, now unflag it and also make it invisible
        //{
            //View itemView=listView.getChildAt(currQues);
            //ImageButton imgBtn=itemView.findViewById(R.id.flag);
            flagQues[currQues]=!flagQues[currQues];               //toggle value
            adapter.notifyDataSetChanged();
        //}
    }

    public void submit(View view) //this function will be called on finish of counter and for itself
    {
        submitFlag=true;
        //also ask for confirmation
        //if user entered inside +ve button then leave a flag of enterence(true), and if entered inside onFinish then also leave a flag

        //(submit) flag will prevent onFinish to callFunction, if user entered yes then display if no then also check finish flag before closing quiz
        //if finish flag is true then close quiz otherwise not and make submit flag false(so finish could understand that either submit is working or not)



        AlertDialog.Builder builder=new AlertDialog.Builder(StartQuizM023.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure to submit quiz?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeQuiz();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(finishFlag)     //user reject submission but also check either time finishes or not
                    closeQuiz();
            }
        });
        Dialog dialog= builder.create();
        dialog.show();
        submitFlag=false;

        //submit quiz, open dialogue and show result with 2 buttons, ok and see Details, on clicking of
        //see details open new actiity withh a buuton named ok(naigate to main page) and show their a recler list of wrong/correct ques with red and green back and with button
        //on clicking of button details of that mcq will be shown on dialogue
    }
    //on clicking of any button in recycler view of all make curQUes outer to white, then set curr to cliked button and generate quiz
    public void closeQuiz()
    {
        //now here show a dialogue box
        //

        AlertDialog.Builder builder=new AlertDialog.Builder(StartQuizM023.this);
        builder.setTitle("Result");
        builder.setMessage("Correct answers "+countMarks()+" out of "+RecViewAdpaterForSub.mcqCount);
        builder.setCancelable(false);
        builder.setPositiveButton("See Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                //make their listView having Q#1, and background green or red
                //

                Intent intent=new Intent(StartQuizM023.this,ResultDetails.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(StartQuizM023.this,MainActivity.class); //quiz is over go back to main page
                startActivity(intent);
            }
        });
        Dialog dialog= builder.create();
        dialog.show();
    }

    int countMarks()
    {
        int count=0;
        for(int i=0;i<RecViewAdpaterForSub.mcqCount;i++)
            if((RecViewAdpaterForSub.quizQuestions.get(i).getKey().charAt(0)-'A')+1==userAnswers[i]) //key matched
                count++;
        return count;
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        DBHelper.storeBundle(new BundleM023(currQues,userAnswers,flagQues,solved,finishFlag,submitFlag,tempSec));
        Bundle bundle=new Bundle();
        bundle.putBoolean("rotate",true);
        timerSave.cancel();
    }

    //@Override
    //protected void onStop() {  //it will be called when screen rotate and activity destroyed
    //    super.onStop();
    //    DBHelper.storeBundle(new BundleM023(currQues,userAnswers,flagQues,solved,finishFlag,submitFlag,tempSec));
    //    Bundle bundle=new Bundle();
    //    bundle.putBoolean("rotate",true);
    //    //also make true onSaveState
    //}
}