package com.a2zdaddy.myquizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mQusetionRef,mStatousref;
    private TextView mScoreView,questioncountno;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4,quit;
    private TextView mTextViewCountDown;
    private static final long START_TIME_IN_MILLIS = 10000;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
     Button startbutton;




    private String qno;
    private String mAnswer;
    private int mScore = 0;
     public int mQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        quit=findViewById(R.id.quit);
           questioncountno=findViewById(R.id.qustioncount);
           startbutton=findViewById(R.id.startbtn);
        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
         mButtonChoice4=findViewById(R.id.choice4);
         updateQuestion();
startbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startTimer();
        startbutton.setVisibility(View.GONE);
    }
});

         quit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                 intent.putExtra("score",Integer.toString(mScore));
                 intent.putExtra("outof",qno);

                 startActivity(intent);
                 finish();



             }
         });

        //Start of Button Listener for Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice1.getText().equals(mAnswer)){

                    mScore = mScore + 1;
                    updateScore(mScore);
                    //This line of code is optiona
                    //Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }



                else {
                    Toast.makeText(MainActivity.this, "Wrong, Ans is: "+mAnswer, Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button1

        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice2.getText().equals(mAnswer)) {
                    mScore = mScore + 1;
                    updateScore(mScore);
                    //This line of code is optiona
                    //Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                    updateQuestion();


                }

            else {
                    Toast.makeText(MainActivity.this, "Wrong, Ans is: "+mAnswer, Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button2


        //Start of Button Listener for Button3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice3.getText().equals(mAnswer)){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    //This line of code is optiona
                    //Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    updateQuestion();


                }else {
                    Toast.makeText(MainActivity.this, "Wrong, Ans is: "+mAnswer, Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button3
        //Start of Button Listener for Button4
        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                if (mButtonChoice4.getText().equals(mAnswer)){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();
                    //This line of code is optiona
                    //Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "Wrong, Ans is: "+mAnswer, Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });


    }

    private void updateQuestion(){

        mQusetionRef=FirebaseDatabase.getInstance().getReference().child(String.valueOf(mQuestionNumber));
        Log.d( "updateQuestion: ","databaseref for question"+mQusetionRef);

            mQusetionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("onDataChange: ", "datasnapsohot for question" + dataSnapshot);

                    String question = dataSnapshot.child("question").getValue(String.class);
                    mQuestionView.setText(question);
                    String choice1 = dataSnapshot.child("choice1").getValue(String.class);
                    mButtonChoice1.setText(choice1);
                    String choice2 = dataSnapshot.child("choice2").getValue(String.class);
                    mButtonChoice2.setText(choice2);
                    String choice3 = dataSnapshot.child("choice3").getValue(String.class);
                    mButtonChoice3.setText(choice3);
                    String choice4 = dataSnapshot.child("choice4").getValue(String.class);
                    mButtonChoice4.setText(choice4);
                    mAnswer = dataSnapshot.child("answer").getValue(String.class);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                }
            });


        mStatousref=FirebaseDatabase.getInstance().getReference().child(String.valueOf(mQuestionNumber));
             mStatousref.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     if(dataSnapshot.exists()){
                         mQuestionNumber++;

                          qno=Integer.toString(mQuestionNumber);
                          questioncountno.setText(qno);


                     }
                     else {
                         Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                         intent.putExtra("score",Integer.toString(mScore));
                         intent.putExtra("outof",qno);

                         startActivity(intent);
                         finish();



                     }

                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });


        }




    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }


    //timer
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("score",Integer.toString(mScore));
                intent.putExtra("outof",qno);

                startActivity(intent);
                finish();



            }
        }.start();


    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }


}

