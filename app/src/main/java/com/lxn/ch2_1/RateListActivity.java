package com.lxn.ch2_1;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
String data[]={"wait..."};
Handler handler;
private String logDate="";
    private final String DATE_SP_KEY="lastRateDateStr";
    private final String TAG="Ratelist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp=getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate=sp.getString(DATE_SP_KEY,"");
        Log.i("list","lastDateStr="+logDate);
        //setContentView(R.layout.activity_rate_list);
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 101; i++) {
            list1.add("item" + i);
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    @SuppressLint("HandlerLeak") List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<>(RateListActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
//获取网络数据，放入List带回主线程
        List<String> retList=new ArrayList<>();
String curDateStr=(new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
Log.i("run","curDateStr="+curDateStr+" logDate="+logDate);

if(curDateStr.equals(logDate)){
    Log.i("run","从数据库获得数据");
    RateManager manager=new RateManager(this);
                for(RateItem i:manager.listAll()){
               retList.add(i.getCurName()+"---->"+i.getCurRate());
            }

}else {
    Document doc;
    Log.i("run","从网络获得数据");
    try {
        Thread.sleep(3000);
        doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
        Log.i(TAG, "run:" + doc.title());
        Elements tds = doc.getElementsByTag("td");
List<RateItem> rateList=new ArrayList<RateItem>();
        for (int i = 0; i < tds.size(); i += 6) {
            Element td1 = tds.get(i);
            Element td2 = tds.get(i + 5);
            Log.i(TAG, "run:text=" + td1.text() + "==>" + td2.text());

            String str1 = td1.text();
            String val = td2.text();
            retList.add(td1.text() + "==>" + td2.text());
rateList.add(new RateItem(str1,val));
        }
//把数据写入数据库
        RateManager manager=new RateManager(this);
        manager.deleteAll();
        manager.addAll(rateList);
        //更新记录日期
        SharedPreferences sp=getSharedPreferences("myrate",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString(DATE_SP_KEY,curDateStr);
        edit.commit();
        Log.i("run","更新日期结束："+curDateStr);

    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}
        //获取MSG对象，用于返回主线程
        Message msg = handler.obtainMessage(7);
        //msg.what = 7;
        msg.obj =retList;
        handler.sendMessage(msg);
    }
}
