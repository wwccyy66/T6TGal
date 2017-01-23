package com.example.dell.t6tgal.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.adapter.ExpandableListAdapter;
import com.example.dell.t6tgal.dbutil.DBUtil;
import com.example.dell.t6tgal.model.News;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/4.
 */

public class NewListActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{


    private List<News> news;
    private ExpandableListAdapter adapter;
    private ExpandableListView newsList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsList= (ExpandableListView) findViewById(R.id.listView);
        initView();
        newsList.setOnItemClickListener(NewListActivity.this);
    }

    private void initView() {
        news=new ArrayList<>(0);

        adapter=new ExpandableListAdapter(this,news,news);
        newsList.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        List<News> list= DBUtil.getData();
        news.addAll(list);
        adapter.notifyDataSetChanged();//更新适配器
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"dddd",Toast.LENGTH_LONG).show();
    }
}
