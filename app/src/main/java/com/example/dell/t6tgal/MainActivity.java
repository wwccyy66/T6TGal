package com.example.dell.t6tgal;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    private static final String TAG="MainActivity";
    private static final int FLAG_TEST=1;
    private ProgressBar progress_bar = null;
    private Button start = null;

    //创建一个Handler，内部完成处理消息方法
    Handler update_progress_bar =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //显示进度条
            progress_bar.setProgress(msg.arg1);
            //重新把进程加入到进程队列中
            update_progress_bar.post(update_thread);
        }
    };
    //runnable接口
    Runnable update_thread=new Runnable() {
            int i=0;
        @Override
        public void run() {
            i += 10;
            //首先获得一个消息结构
            Message msg = update_progress_bar.obtainMessage();
            //给消息结构的arg1参数赋值
            msg.arg1 = i;
            //延时1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //把消息发送到消息队列中
            update_progress_bar.sendMessage(msg);
            if(i == 30)
                //把线程从线程队列中移除
                update_progress_bar.removeCallbacks(update_thread);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void initView() {
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new StartOnClickListenr());
    }

    private class StartOnClickListenr implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //让进度条显示出来
            progress_bar.setVisibility(View.VISIBLE);
            //将线程加入到handler的线程队列中
            update_progress_bar.post(update_thread);
        }
    }
}

