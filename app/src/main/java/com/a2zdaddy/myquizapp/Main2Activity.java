package com.a2zdaddy.myquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
TextView scoretext,questiontext;
Button retrybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        scoretext=findViewById(R.id.marks);
        questiontext=findViewById(R.id.question);
        retrybtn=findViewById(R.id.retrybt);

        Intent intent = getIntent();

        String mScore = intent.getStringExtra("score");
        String mQues = intent.getStringExtra("outof");
         scoretext.setText(mScore);
         questiontext.setText(mQues);


    }

    public void retry(View view) {


        Intent intent=new Intent(Main2Activity.this,MainActivity.class);

        startActivity(intent);
        finish();
    }
}
