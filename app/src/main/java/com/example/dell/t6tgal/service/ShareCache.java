package com.example.dell.t6tgal.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by dell on 2017/2/28.
 */

public abstract  class ShareCache<T> {
    protected Context context;
    protected SharedPreferences shared;

    /**
     * 构造函数
     */
    public ShareCache(Context context){
        this.context=context;
        shared=context.getSharedPreferences(getCacheKey(), Context.MODE_PRIVATE);
    }
    public SharedPreferences getConfig(){
        return shared;
    }
    public void clearConfig(){
       // MobclickAgent.onProfileSignOff();
        shared.edit().clear().commit();
    }
    protected abstract String getCacheKey();

    public abstract T get();

    public abstract void save(T obj);

    protected  void saveObject(String key,T obj){
        ByteArrayOutputStream bos=null;//字节数组输出流
        ObjectOutputStream os=null;//对象输出流
        try {
        String serStr="";
        //保存对象
        SharedPreferences.Editor sharedata=shared.edit();
        if(obj!=null){
            //先将序列化结果写到byte缓存中，其实就是分配一个内存空间
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(obj);//将obj写入到os中
            serStr = bos.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
            LogUtil.d("serial", "serialize str =" + serStr);
            os.close();
            bos.close();
            }
            sharedata.putString(key,serStr);
            sharedata.commit();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("保存obj失败");
        }finally {
            try {
            if (os!=null){
                    os.close();
            }
            if (bos!=null){
                bos.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取一个obj
     * @param key
     * @return
     */
    protected T readObject(String key){
        ByteArrayInputStream bos=null;
        ObjectInputStream os=null;
        try {
        SharedPreferences shareddata=shared;
        if (shareddata.contains(key)){
            String string=shareddata.getString(key,"");
            LogUtil.d("string",string);
            //将16进制的数据转为数组，准备反序列化

                String redStr= java.net.URLDecoder.decode(string, "UTF-8");
                bos=new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
                os=new ObjectInputStream(bos);
                Object obj=os.readObject();
                os.close();
                bos.close();
            return (T) obj;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
            if (os!=null){
                    os.close();
                }
             if (bos!=null){
                 bos.close();
             }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        //所有异常返回null;
        return null;
    }
}
