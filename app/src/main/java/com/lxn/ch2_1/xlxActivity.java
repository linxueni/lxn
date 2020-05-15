package com.lxn.ch2_1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class xlxActivity extends AppCompatActivity implements Runnable{
    EditText key;
    TextView show;
    ListView  list;
    int updateDate;
    private final String TAG="xlx";
    Handler handler;
    String item;
    String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlx);
        key=findViewById(R.id.key_word);
        show=findViewById(R.id.showout);
        list=findViewById(R.id.item);
        //获得sp中保存的数据
        SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences s= PreferenceManager.getDefaultSharedPreferences(this);
        updateDate=sp.getInt("update_date",0);
        //获取当前时间
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY); //美国是以周日为每周的第一天 现把周一设成第一天
        calendar.setTime(today);

        final int todayStr=calendar.get(Calendar.WEEK_OF_YEAR);
        Log.i(TAG , "onCreate: sp updateDate=" +updateDate);
        Log.i(TAG , "onCreate: todayStr=" +todayStr);
        //判断时间
        if(todayStr!=updateDate){
            Log.i(TAG,"需要更新");
            //开启子线程
            Thread t=new Thread(this);
            t.start();
        }else{
            Log.i(TAG,"不需要更新");
        }
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7) {
                    List<String> list2=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(xlxActivity.this,android.R.layout.simple_list_item_1,list2);
                    list.setAdapter(adapter);
                    Log.i(TAG,"onCreate:dg更新");
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("update_date",updateDate);
                    editor.commit();
                    Toast.makeText(xlxActivity.this, "通知已更新", Toast.LENGTH_SHORT).show();
                }

                super.handleMessage(msg);
            }
        };

    }

    public void openOne(View btn){
        keyword = key.getText().toString();
    }
    @Override
    public void run() {
        Log.i(TAG, "run");
        List<String> List =new ArrayList<String>();
            //获取网络数据
            try {
                Document doc = null;
                doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
                Elements lis = doc.getElementsByTag("li");
                for (int i = 65; i < 85; i ++ ) {
                    Elements tds = lis.get(i).getElementsByTag("span");
                    Element td1=tds.get(0);
                    String str1 = td1.text();
                    Log.i(TAG, "run: text=" + td1.text() );
                    List.add(str1);
                //将msg带回到主线程
                Message msg = handler.obtainMessage(7);
                msg.obj = list;
                handler.sendMessage(msg);
                Log.i(TAG, "run: list=" + list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private String inputStream2String(InputStream inputStream) throws  IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for( ; ;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }return out.toString();
    }

}
