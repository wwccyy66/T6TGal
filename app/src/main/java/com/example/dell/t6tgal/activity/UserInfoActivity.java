package com.example.dell.t6tgal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.constant.Constant;
import com.example.dell.t6tgal.model.APIUserRegisterInfo;
import com.example.dell.t6tgal.service.ICallBack;
import com.example.dell.t6tgal.service.ShareTokenCache;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by dell on 2017/3/10.
 */

public class UserInfoActivity extends AppCompatActivity {
    private EditText loginname,loginpwd,username,sex,cellphone;
    private ImageView userImg;
    private ShareTokenCache tokenCache;
    private APIUserRegisterInfo registerInfo;
    private ICallBack callBack;
    private boolean flag;

    public UserInfoActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        initView();
        initData();
    }

    private void initView() {
        tokenCache=new ShareTokenCache(this);
        userImg=(ImageView) findViewById(R.id.info_userImg);
        loginname=(EditText) findViewById(R.id.info_loginname);
        loginpwd=(EditText) findViewById(R.id.info_loginpwd);
        username=(EditText) findViewById(R.id.info_username);
        sex=(EditText) findViewById(R.id.info_loginname);
        cellphone=(EditText) findViewById(R.id.info_cellphone);
    }

    private void initData() {
        //userImg.setImageBitmap(registerInfo.getUserImg());
        final APIUserRegisterInfo userLoginInfo = tokenCache.get();
        if (userLoginInfo==null|| userLoginInfo.getToken()==null){
            callBack.doCallBack(0);
            startLoginActivity();
            return;
        }
        OkHttpUtils.post().url(Constant.URL_userInfo).addParams("loginName",userLoginInfo.getLoginName()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                callBack.doCallBack(0);
                startLoginActivity();
            }

            @Override
            public void onResponse(String response) {
                if (response!=null){
                    Gson gson = new Gson();
                    APIUserRegisterInfo info=gson.fromJson(response,APIUserRegisterInfo.class);
                    loginname.setText(response);
                    username.setText(info.getUserName());
                    sex.setText(info.getUserSex());
                    cellphone.setText(info.getTel());
                    callBack.doCallBack(1);
                }else {
                    tokenCache.clearConfig();
                    callBack.doCallBack(0);
                    startLoginActivity();
                }
            }
        });

    }
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
