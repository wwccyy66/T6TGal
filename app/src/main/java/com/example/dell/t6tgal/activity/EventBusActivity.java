package com.example.dell.t6tgal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.model.MyEvent;
import com.ypy.eventbus.EventBus;

/**
 * Created by dell on 2017/3/16.
 */

public class EventBusActivity extends Activity {

    private TextView tv_content;
    private Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        tv_content= (TextView) findViewById(R.id.tv_content);
        btn_send= (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEvent myEvent=new MyEvent();
                myEvent.setType("0");
                myEvent.setType("1");
                myEvent.setContent("0内容");
                myEvent.setContent("1内容");
                EventBus.getDefault().post(myEvent);
            }
        });
        EventBus.getDefault().register(this);//注册
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }


    /**
     * 默认接收方式
     *接收事件
     * 接收类型和发送类型一致
     */
    public void onEvent(MyEvent myEvent){
       if (myEvent.getType().equals("0")){
           tv_content.setText(myEvent.getContent());
       }
    }

    /**
     * 主线程接收
     */
    public void onEventMainThread(MyEvent myEvent){
        if (myEvent.getType().equals("1")){
            tv_content.setText(myEvent.getContent());
        }
    }



}
