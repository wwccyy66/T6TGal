package com.example.dell.t6tgal.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.activity.NewListActivity;
import com.example.dell.t6tgal.activity.ViewPagerActivity;
import com.example.dell.t6tgal.customapplication.MyApplication;
import com.example.dell.t6tgal.model.MyEvent;
import com.ypy.eventbus.EventBus;

import java.net.URL;

/**
 * Created by dell on 2017/1/10.
 */

public class Fragment2 extends Fragment {
    private TextView tv_content,tv_send;
    private Button btn_send;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view2,container,false);
        tv_content= (TextView)v.findViewById(R.id.tv_content);
        btn_send= (Button) v.findViewById(R.id.btn_send);
        tv_send= (TextView) v.findViewById(R.id.tv_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEvent myEvent=new MyEvent();
                myEvent.setType("0");
                myEvent.setContent(tv_send.getText().toString());
                EventBus.getDefault().post(myEvent);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销
    }

    public void onEvent(MyEvent myEvent){
        if (myEvent.getType().equals("0")){
            tv_content.setText(myEvent.getContent());
            tv_send.setText("");
        }
    }

}
