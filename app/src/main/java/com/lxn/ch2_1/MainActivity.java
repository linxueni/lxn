package com.lxn.ch2_1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
     TextView out;
     EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add something..
        out= findViewById(R.id.textView);
        edit= findViewById(R.id.editText);
        Button btn= findViewById(R.id.button);
    }

    @Override
    public void onClick(View v) {
        String str=edit.getText().toString();
        float p= Float.parseFloat(str);
        String s= String.valueOf((p*5/9)+32);
        out.setText("华氏温度"+s);
        Log.i("click","onClickcalled");
    }
    public void btnClick(View v) {

        Log.i("click","btnClick");
    }
}
