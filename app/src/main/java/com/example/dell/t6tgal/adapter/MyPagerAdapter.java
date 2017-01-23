package com.example.dell.t6tgal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dell on 2017/1/10.
 * 数据源 List viewList
 */

public class MyPagerAdapter  extends PagerAdapter {
    private List<View> viewList=null;
    private List titleList=null;
    private Context context;
    /**
     * 构造适配器
     */
    public MyPagerAdapter(Context context,List titleList,List<View> viewList){
        this.context=context;
        this.viewList=viewList;
        this.titleList=titleList;
    }

    /**
     * 实例化一个页卡
     * @param container
     * @param position
     * @return
     */

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    /**
     * 销毁一个页卡
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    /**
     * 获取view列表的大小
     * @return
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
