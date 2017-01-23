package com.example.dell.t6tgal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dell on 2017/1/10.
 */

public class MyFragmentPagerAdapter  extends FragmentPagerAdapter{

    //数据源
    private List<Fragment> fragList = null;//fragment集合
    private List titleList=null;//标题集合
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragList,List titleList) {
        super(fm);
        this.fragList = fragList;
        this.titleList = titleList;
    }
    /**
     * 获取页面title
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return ((String[]) titleList.get(position))[position];
    }



    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}
