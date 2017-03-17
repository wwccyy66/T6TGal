package com.example.dell.t6tgal.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.cardemulation.OffHostApduService;
import android.util.Log;
import android.webkit.ClientCertRequest;
import android.widget.Toast;

import com.example.dell.t6tgal.activity.ViewPagerActivity;
import com.example.dell.t6tgal.constant.Constant;
import com.example.dell.t6tgal.model.News;
import com.lidroid.xutils.http.RequestParams;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by dell on 2017/1/4.
 */

public class CommonGap  {

    //与服务器交互地址 Constant.BASE_URL;
    //activity、service

    private static AsyncHttpClient client = new AsyncHttpClient();
    private Activity activity;

    private Service service;

    // 最大线程数
    private static final int MAX_PROGRESS = 100;
    // 对话框
    private ProgressDialog progressDialog;

    News news=null;

    public Context getContext(){
        if (activity!=null){
            return activity;
        } else  if(null!=service){
            return service;
        }else{
            return null;
        }
    }
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    /**
     * activity构造器
     */
    public CommonGap(Activity activity){
        this.activity=activity;
    }
    /**
     * service构造器
     */
    public CommonGap(Service service){
        this.service=service;
    }


    /**
     * 提交登陆请求：
     *
     */
    public void postLogin(String key,String value){

        OkHttpUtils.post().url(Constant.URL_Login).addParams(key,value).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("233333333","失败");
            }

            @Override
            public void onResponse(String response) {
                Log.e("233333强强强强333",response);
                if (response.equals("true")){
                    Toast.makeText(getContext(),"登陆成功",Toast.LENGTH_LONG).show();

                }else if (response.equals("false")){
                    Toast.makeText(getContext(),"登陆失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void postMap(Map<String,String>  map){
        OkHttpUtils.post().url(Constant.URL_Login).params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("233333333","失败");
            }

            @Override
            public void onResponse(String response) {
                Log.e("233333强强强强333",response);
            }
        });
    }
    public String postLogin(){
        OkHttpUtils.get().url(Constant.BASE_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("22222","233322");
            }

            @Override
            public void onResponse(String response) {
                Log.e("22222",response);
            }
        });
        return  null;
    }
    /**
     * 提交字符串到服务器
     * @param url
     * @param data
     */
    public void postJson(String url,String data){
        OkHttpUtils.postString().url(url).content(data).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("233333111111133","失败");
            }

            @Override
            public void onResponse(String response) {
                Log.e("233333强强强强333",response);
            }
        });
    }

    /**
     * 直接上传文件
     * @param url
     * @param file
     */
    public void  postFile(String url,File file){
        OkHttpUtils.postFile().url(url).file(file).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    /**
     * 提交文件到服务器
     */
    public void postFiles(String url,String filePath){
        OkHttpUtils.post().url(url).addFile("attach","IM.APK",new File(filePath)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.d("fwe3333333333333",response);
            }
        });
    }

    /**
     * 文件下载
     */
    public void getFile(String url,String filePath,String fileName,String title,String message){
        onprocessDialog(title,message);
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(filePath, fileName) {
            @Override
            public void inProgress(float progress, long total) {

            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(File response) {

            }
        });
    }

    public void getImag(String url){

        OkHttpUtils.get().url(url).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Bitmap response) {

            }
        });

    }
    public News getNews (String url){
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("------------->","onError");
            }

            @Override
            public void onResponse(String response) {
                Log.e("----2222--------->",response);
//                news.setTitle(response);
            }
        });
        return news;
    }
    public News PostNews (String url){
        OkHttpUtils.post().url(url).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                Log.e("------------->","onError");
            }

            @Override
            public void onResponse(String response) {
                Log.e("------------->",response);
                Log.d("dhhthdfhf44444444dhdh","444444444rger");
                Log.d("dhhthdfhfdhdh",response);
                news.setTitle(response);
            }
        });
        return news;
    }
    protected void onprocessDialog() {
        // progressDialog = new ProgressDialog(activity);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Connect to Server...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected void onprocessDialog(String title){
        onprocessDialog();
        progressDialog.setTitle(title);
    }
    protected void onprocessDialog(String title, String message) {
        onprocessDialog(title);
        progressDialog.setMessage(message);


    }
    }