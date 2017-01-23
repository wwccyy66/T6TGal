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
    public DBUtil( Activity activity){
        this.activity=activity;
    }

    public static  List<News> getData(){
        List<News> newsList=new ArrayList<>(0);
        CommonGap commonGap=new CommonGap(activity);
        News news=commonGap.getNews(Constant.BASE_URL+"url");
//        newsList.add(news);
        newsList.add(new News("天皇至尊版1","赵浩老大爷",R.mipmap.ic_launcher,1000*50*5));
        newsList.add(new News("天皇至尊版2","赵浩老大爷",R.mipmap.ic_launcher,1000*20*20));
        newsList.add(new News("天皇至尊版3","赵浩老大爷",R.mipmap.ic_launcher,2000*60*60));
        newsList.add(new News("天皇至尊版4","赵浩老大爷",R.mipmap.ic_launcher,2000*60*60));
        newsList.add(new News("天皇至尊版5","赵浩老大爷",R.mipmap.ic_launcher,1000*60*20));
        return newsList;
    }
}
