package com.example.dell.t6tgal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.constant.Constant;
import com.example.dell.t6tgal.model.APIUserRegisterInfo;
import com.example.dell.t6tgal.service.ICallBack;
import com.example.dell.t6tgal.service.ShareTokenCache;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.regex.Pattern;
import okhttp3.Call;

/**
 * Created by dell on 2017/3/1.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG=LoginActivity.class.getSimpleName();
    private View forget;
    private TextView register,login;
    private Intent intent;
    private Context mContext;
    private EditText phoneEditText, pwdEditText;
    private String phone, pwd;
    private ShareTokenCache  tokenCache;
    private APIUserRegisterInfo registerInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //单列模式，关闭所有activity，只开启一个登陆界面
        /*JiaYouApplication.getInstance().finishAllActivity();
        JiaYouApplication.getInstance().addActivity(LoginActivity.this);*/
        this.mContext=this;
        initData();
        initView();

    }

    //初始化数据
    public void initData(){
        intent=getIntent();
    }
    /**
     * 初始化界面
     */
    private void initView() {
        tokenCache=new ShareTokenCache(this);
        this.forget = findViewById(R.id.login_forget);
        this.forget.setOnClickListener(this);
        this.login = (TextView) findViewById(R.id.login_text);
        this.login.setOnClickListener(this);
        this.register = (TextView) findViewById(R.id.login_register);
        this.register.setOnClickListener(this);
        this.phoneEditText = (EditText) findViewById(R.id.login_phone);
        this.pwdEditText = (EditText) findViewById(R.id.login_pwd);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_forget:

                break;
            case R.id.login_text:
                phone=phoneEditText.getText().toString().trim();
                pwd=pwdEditText.getText().toString().trim();
                if (phone.equals("")){
                    Toast.makeText(this,"请出入用户名",Toast.LENGTH_LONG).show();
                }else if (pwd.equals("")){
                    Toast.makeText(this,"请出入密码",Toast.LENGTH_LONG).show();
                }else if (Pattern.matches("^1\\d{10}$", phone)){
                    Toast.makeText(this,"请出入正确的用户名",Toast.LENGTH_LONG).show();
                }else if (pwd.length()<6||pwd.length()>20){
                    Toast.makeText(this,"请出入6-20位密码",Toast.LENGTH_LONG).show();
                }else{
                    //String json="{\"username\":\" "+phone+"\",\"password\" :"+pwd+"}";
                   /* RequestParams params=new RequestParams();
                    params.put("username",phone);
                    params.put("password",pwd);*/
                    /*Map<String,String> map=new HashMap<>();
                    map.put("username",phone);
                    map.put("password",pwd);*/
                    String loginInfo=phone+","+pwd;
                    /*CommonGap commonGap=new CommonGap(this);
                    commonGap.postLogin("loginInfo",loginInfo);*/
                    OkHttpUtils.post().url(Constant.URL_Login).addParams("loginInfo",loginInfo).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(mContext,"连接失败请检查网络",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(String response) {

                            if (response.equals("true")){
                                Toast.makeText(mContext,"登陆成功",Toast.LENGTH_LONG).show();
                                intent = new Intent(mContext, ViewPagerActivity.class);
                                startActivity(intent);

                            }else if (response.equals("false")){
                                Toast.makeText(mContext,"登陆失败",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(mContext,"未知原因导致失败",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
            case R.id.login_register:
                intent = new Intent(mContext, RegisterUserActivity.class);
                startActivity(intent);
                break;
        }
    }
}
