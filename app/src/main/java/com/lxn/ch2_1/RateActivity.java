package com.lxn.ch2_1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
     EditText rmb;
     TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb=findViewById(R.id.rmb);
        show=findViewById(R.id.showout);
    }
    public void onClick(View btn){
        String str=rmb.getText().toString();//获取用户输入类型
        float f=0;
        if(str.length()>0){
             f=Float.parseFloat(str);
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        float val=0;
        if(btn.getId()==R.id.dollar){
             val=f*(1/6.7f);//强制转换
        }
        else if(btn.getId()==R.id.euro){
             val=f*(1/11f);//强制转换
        }
        else{
             val=f*500;
        }
        show.setText(String.valueOf(val));


    }
}
