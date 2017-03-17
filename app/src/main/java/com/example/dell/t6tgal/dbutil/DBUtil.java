package com.example.dell.t6tgal.dbutil;

import android.app.Activity;
import android.app.Service;
import android.content.Context;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.activity.NewListActivity;
import com.example.dell.t6tgal.constant.Constant;
import com.example.dell.t6tgal.model.News;
import com.example.dell.t6tgal.service.CommonGap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/4.
 */

public class DBUtil {

    private static Activity activity;
    public static String filePath="D:\\2.docx";
    public DBUtil( Activity activity){
        this.activity=activity;
    }

    public static  List<News> getData(){
        List<News> newsList=new ArrayList<>(0);
        CommonGap commonGap=new CommonGap(activity);
        commonGap.postFiles(Constant.BASE_URL,filePath);
//        newsList.add(news);
        return newsList;
    }

}
