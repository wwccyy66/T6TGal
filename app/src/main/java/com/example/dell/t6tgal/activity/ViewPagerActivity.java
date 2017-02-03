package com.example.dell.t6tgal.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.dell.t6tgal.Fragment.CursorLoaderListFragment1;
import com.example.dell.t6tgal.Fragment.Fragment1;
import com.example.dell.t6tgal.Fragment.Fragment2;
import com.example.dell.t6tgal.Fragment.Fragment3;
import com.example.dell.t6tgal.Fragment.Fragment4;
import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.adapter.MyFragmentPagerAdapter;
import com.example.dell.t6tgal.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/10.
 */

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager pager=null;
    private MyPagerAdapter adapter=null;
    private View[] views = new View[4];
    private int[] viewId = {R.layout.view1, R.layout.view2, R.layout.view3, R.layout.view4};
    private String[] titleStr = {"第一页", "第二页", "第三页", "第四页"};
    //数据源
    private List viewList = null;
    private List<String[]> titleList = null;
    //标题
    private PagerTabStrip tab = null;
    private List fragList = null;
    private MyFragmentPagerAdapter myFragmentPagerAdapter=null;
    private WebView wb=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vewpage);
        initViews();

        wb = (WebView) findViewById(R.id.wb);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");
        wb.loadUrl("file:///android_asset/web.html");


        tab.setDrawFullUnderline(false);
        adapter=new MyPagerAdapter(this,titleList,viewList);
        pager.setAdapter(myFragmentPagerAdapter);
        pager.setOnPageChangeListener(this);
    }

    private void initViews() {
        pager=(ViewPager)findViewById(R.id.id_viewpager);
        tab = (PagerTabStrip) findViewById(R.id.id_tab);
        fragList = new ArrayList<>();
        viewList = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i=0;i<viewId.length;i++){
            views[i] = View.inflate(this, viewId[i], null);
            viewList.add(views[i]);
        }
        for (int i=0;i<titleStr.length;i++){
            titleList.add(titleStr);
        }
        fragList.add(new CursorLoaderListFragment1());
        fragList.add(new Fragment2());
        fragList.add(new Fragment3());
        fragList.add(new Fragment4());
        //Fragment适配器
        myFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragList,titleList);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "这是第" + (position + 1) + "个页面", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
class WebViewJavaScriptInterface {
    private Context context;
    public WebViewJavaScriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void makeToast(String message, boolean lengthLong) {
        Toast.makeText(context, message, (lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)).show();
    }

}