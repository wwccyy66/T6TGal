package com.example.dell.t6tgal.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.service.ICallBack;
import com.example.dell.t6tgal.service.YouTest;

/**
 * Created by dell on 2017/1/10.
 */

public class Fragment3 extends Fragment {
    private Button btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view3,container,false);
        btn= (Button) v.findViewById(R.id.btn_answer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasQuestion();
            }
        });
        return v;
    }

    private void hasQuestion(){
        YouTest you=new YouTest();

        you.setCallBack("五牛", "这个题目怎么做啊", new ICallBack() {
            @Override
            public void doCallBack(int i) {

            }

            @Override
            public void callBackByDl(String str) {
                Log.d("-------22222>>","我说：嗯，好的，我收到答案了:"+str);
            }
        });
        new Thread(you).start();
    }
}
