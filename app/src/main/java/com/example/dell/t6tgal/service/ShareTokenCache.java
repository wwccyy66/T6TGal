package com.example.dell.t6tgal.service;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;

import com.example.dell.t6tgal.activity.LoginActivity;
import com.example.dell.t6tgal.constant.Constant;
import com.example.dell.t6tgal.model.APIUserLoginInfo;
import com.example.dell.t6tgal.model.APIUserRegisterInfo;
import com.example.dell.t6tgal.utils.Utils;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.squareup.okhttp.internal.spdy.Header;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by dell on 2017/2/28.
 */

public class ShareTokenCache extends ShareCache<APIUserRegisterInfo> {
    private String key="token";
    /**
     * 构造函数
     *
     * @param context
     */
    public ShareTokenCache(Context context) {
        super(context);
    }

    @Override
    protected String getCacheKey() {
        return "token_cache";
    }

    @Override
    public APIUserRegisterInfo get() {
        return super.readObject(this.key);
    }

    @Override
    public void save(APIUserRegisterInfo obj) {
        super.saveObject(this.key,obj);
        JPushUtils.setSAlias(context);
    }
    public void checkToken(final ICallBack callBack, final boolean... parameters){
        final APIUserRegisterInfo userLoginInfo = get();
        if (userLoginInfo==null|| userLoginInfo.getToken()==null){
            callBack.doCallBack(0);
            startLoginActivity();
            return;
        }
        OkHttpUtils.post().url(Constant.URL_userInfo).addParams("token",userLoginInfo.getToken()).build().execute(new StringCallback() {
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
                    info.getLoginName();
                    info.getUserName();
                    info.getUserSex();
                    callBack.doCallBack(1);
                }else {
                    clearConfig();
                    callBack.doCallBack(0);
                    startLoginActivity();
                }
            }
        });
    }


    private void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


}
