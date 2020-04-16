package com.lxn.ch2_1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG ="second" ;
    TextView score;
 TextView score2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate:");
        setContentView(R.layout.activity_second);
        score=findViewById(R.id.score1);
        score2=findViewById(R.id.score2);
    }
//保存内容！
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea=((TextView)findViewById(R.id.score1)).getText().toString();
        String scoreb=((TextView)findViewById(R.id.score2)).getText().toString();
        outState.putString("teama-score",scorea);
        outState.putString("teamb-score",scoreb);
        Log.i(TAG,"onSaveInstanceState:");
    }
//获得内容
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea=savedInstanceState.getString("teama-score");
        String scoreb=savedInstanceState.getString("teamb-score");
        ((TextView)findViewById(R.id.score1)).setText(scorea);
        ((TextView)findViewById(R.id.score2)).setText(scoreb);
        Log.i(TAG,"onRestoreInstanceState:");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart:");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume:");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart:");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause:");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy:");
    }

    public void btnAdd1(View v) {
        if(v.getId()==R.id.button_4)
      showScore(1);
        else{
            showScore2(1);
        }
    }
    public void btnAdd2(View v) {
        if(v.getId()==R.id.button_5)
            showScore(2);
        else{
            showScore2(2);
        }
    }
    public void btnAdd3(View v) {
        if(v.getId()==R.id.button_6)
            showScore(3);
        else{
            showScore2(3);
        }
    }
    public void reset(View v) {
        score.setText("0");
        score2.setText("0");
    }
    public void showScore(int inc) {
        Log.i("main","inc="+inc);
     String oldScore=(String) score.getText();
     int newScore=Integer.parseInt(oldScore)+inc;
     score.setText(""+newScore);
    }
    public void showScore2(int inc) {
        Log.i("main","inc="+inc);
        String oldScore=(String) score2.getText();
        int newScore=Integer.parseInt(oldScore)+inc;
        score2.setText(""+newScore);
    }
}
