package com.example.dell.t6tgal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.constant.Constant;
import com.example.dell.t6tgal.model.APIUserRegisterInfo;
import com.example.dell.t6tgal.service.ShareTokenCache;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by dell on 2017/3/1.
 */
public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText loginname,loginpwd,username,sex,cellphone;
    private ImageView userImg;
    private Button saveBtn;
    private APIUserRegisterInfo userRegisterInfo;
    private String jsonData=null;
    private Context mContext;
    private ShareTokenCache tokenCache;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registxeruser);
        mContext=this;
        iniView();
    }

    private void initData() {
        String rloginname=loginname.getText().toString().trim();
        String rloginpwd=loginpwd.getText().toString().trim();
        String rusername=username.getText().toString().trim();
        String rsex=sex.getText().toString().trim();
        String rcellphone=cellphone.getText().toString().trim();
        String ruserImg=userImg.getDrawable().toString().trim();
        userRegisterInfo=new APIUserRegisterInfo();
        userRegisterInfo.setLoginName(rloginname);
        userRegisterInfo.setPassword(rloginpwd);
        userRegisterInfo.setUserName(rusername);
        userRegisterInfo.setUserSex(rsex);
        userRegisterInfo.setTel(rcellphone);
        userRegisterInfo.setUserImg(ruserImg);
        userRegisterInfo.setToken(rloginname);
        Gson gson=new Gson();
        jsonData=gson.toJson(userRegisterInfo);
    }

    public void iniView(){
        tokenCache=new ShareTokenCache(this);
        userImg=(ImageView) findViewById(R.id.userImg);
        userImg.setOnClickListener(this);
        loginname=(EditText) findViewById(R.id.loginname);
        loginpwd=(EditText) findViewById(R.id.loginpwd);
        username=(EditText) findViewById(R.id.username);
        sex=(EditText) findViewById(R.id.loginname);
        cellphone=(EditText) findViewById(R.id.cellphone);
        saveBtn=(Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userImg:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 3);
                break;
            case R.id.saveBtn:
                initData();
                OkHttpUtils.post().url(Constant.URL_save).addParams("registerInfo",jsonData).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext,"保存失败请检查网络",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("true")){
                            tokenCache.save(userRegisterInfo);
                            Toast.makeText(mContext,"保存成功",Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(mContext,"保存失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }


    }
    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case 3:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        userImg.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("tag",e.getMessage());
                        Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.i("liang", "失败");
                }

                break;

            default:
                break;
        }
        }

}
