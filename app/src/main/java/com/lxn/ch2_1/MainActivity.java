package com.lxn.ch2_1;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
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
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.i("mail","onClick called....");
                String str=edit.getText().toString();
                float s= Float.parseFloat(str);
                String result= String.valueOf((s*5/9)+32);
                out.setText("结果为"+result);
            }
            }
        );
    }

}
