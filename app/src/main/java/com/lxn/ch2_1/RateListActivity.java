package com.lxn.ch2_1;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class RateListActivity extends ListActivity {
String data[]={"one","two","three"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);不能有！！
        ListAdapter adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);

    }
}
