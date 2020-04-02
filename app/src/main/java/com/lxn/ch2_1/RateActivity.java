package com.lxn.ch2_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
     EditText rmb;
     TextView show;
     private final String TAG="Rate";
     private float dollarRate=0.1f;
    private float euroRate=0.2f;
    private float wonRate=0.3f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb=findViewById(R.id.rmb);
        show=findViewById(R.id.showout);
    }
    public void onClick(View btn){
        String str=rmb.getText().toString();//获取用户输入类型
        float r=0;
        if(str.length()>0){
             r=Float.parseFloat(str);
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
       // float val=0;
        if(btn.getId()==R.id.dollar){
            show.setText(String.format("%.2f",r*dollarRate));
            // val=f*(1/6.7f);//强制转换
        }
        else if(btn.getId()==R.id.euro){
            show.setText(String.format("%.2f",r*euroRate));
             //val=r*(1/11f);//强制转换
        }
        else{
            show.setText(String.format("%.2f",r*wonRate));
             //val=r*500;
        }
        //show.setText(String.valueOf(val));

    }
    public void openOne(View btn){
        //Log.i("open","openOne:");
        //Intent hello=new Intent(this,SecondActivity.class);
        //Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
       // Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:13880321454"));
        Intent config=new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        Log.i("openone","dollarRate="+dollarRate);
        Log.i("openone","euroRate="+euroRate);
        Log.i("openone","wonRate="+wonRate);
        //startActivity(config);
        startActivityForResult(config,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 &&resultCode==2 ){
            /*bdl.putFloat("key_dollar",newDollar);
            bdl.putFloat("key_euro",newEuro);
            bdl.putFloat("key_won",newWon);*/
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("key_dollar",0.1f);
            euroRate=bundle.getFloat("key_euro",0.1f);
            wonRate=bundle.getFloat("key_won",0.1f);
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate);
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /* public void startTimer(String message, int seconds) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void addEvent(String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }*/
}
