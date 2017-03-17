package com.example.dell.t6tgal.service;

import android.util.Log;

/**
 * Created by dell on 2017/3/16.
 */

public class YouTest  implements Runnable{

    private String who;
    private ICallBack callBack;

    public YouTest(){

    }

    /**
     *调用此方法表示有人联系你了，注册到你这里来了
     */
    public void setCallBack(String who,String question,ICallBack callBack){

        this.who=who;
        Log.d("------------->","你说：当前联系到我的人是"+who+"，问题是"+question);
        this.callBack=callBack;
    }

    public void handleThings(){
        //假如你现在正在想问题的答案，需要一点时间
        for(int i=0;i<100000;i++){
            if(i == 0){
                Log.d(">>>>>","你正在思考问题.....");
            }
        }
        String answer="答案是小猪";

        //想到问题的办法了
        Log.d("-===========","你说：想到答案了，准备打回去给"+who+"告诉他答案");
        callBack.callBackByDl(answer);
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            handleThings();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
