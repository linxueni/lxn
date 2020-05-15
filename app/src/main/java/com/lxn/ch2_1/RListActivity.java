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
import java.util.HashMap;
import java.util.List;

public class RListActivity extends ListActivity implements Runnable{
Handler handler;
    private final String TAG="RList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    @SuppressLint("HandlerLeak") List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<>(RListActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
//获取网络数据，放入List带回主线程
        List<HashMap<String, String>> rateList=new ArrayList<HashMap<String, String>>();
        Document doc;
        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
            Elements lis = doc.getElementsByTag("li");
            for(int j=65;j<85;j++){
                Elements spans = lis.get(j).getElementsByTag("span");
                String url0 = lis.get(j).getElementsByTag("a").attr("href");
                String url1 = "https://it.swufe.edu.cn/".concat(url0.substring(2));
                Log.i(TAG,"run: url["+j+"]=" + url1);
                Element span1 = spans.get(0);
                Element span2 = spans.get(1);
                String text = span1.text();
                String time = span2.text();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle",text);
                map.put("ItemTime",time);
                map.put("Url",url1);
                rateList.add(map);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //获取MSG对象，用于返回主线程
        Message msg = handler.obtainMessage(7);
        //msg.what = 7;
        msg.obj =rateList;
        handler.sendMessage(msg);
    }
}
