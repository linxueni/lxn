package com.lxn.ch2_1;

import android.annotation.SuppressLint;
import android.app.ListActivity;
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
import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
String data[]={"wait..."};
Handler handler;
    private final String TAG="Ratelist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
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

        Document doc;

        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();

            Log.i(TAG, "run:" + doc.title());

            Elements tds = doc.getElementsByTag("td");

            for (int i = 0; i < tds.size(); i += 6) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                Log.i(TAG,"run:text="+td1.text()+"==>"+td2.text());

                String str1 = td1.text();
                String val = td2.text();
                retList.add(td1.text()+"==>"+td2.text());

            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //获取MSG对象，用于返回主线程
        Message msg = handler.obtainMessage(7);
        //msg.what = 7;
        msg.obj =retList;
        handler.sendMessage(msg);
    }
}
