package com.example.dell.t6tgal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.adapter.ImageListAdapter;
import com.example.dell.t6tgal.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 2017/3/15.
 */

public class LruCacheActivity extends Activity {
    //获取bitmapUtils单列
    public static BitmapUtils bitmapUtils;
    private String[] imgSites = { "http://image.baidu.com/",
            "http://www.22mm.cc/", "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/", "http://www.youzi4.com/" };
    private ListView imageListView;
    private ImageListAdapter imageListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrucache);
        //获取bitmapUtils单列
        bitmapUtils= BitmapHelp.getBitmapUtils(this);
        // 获取listView控件
        imageListView = (ListView) findViewById(R.id.img_list);
        /**
         * 设置默认的图片展现、加载失败的图片展现
         */
        bitmapUtils.configDefaultLoadingImage(R.drawable.bac3);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.imag2);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);

        // 设置最大宽高, 不设置时根据控件属性自适应.
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(
                this).scaleDown(3));

        /**
         * 滑动时加载图片，快速滑动时不加载图片 com.lidroid.xutils.bitmap.PauseOnScrollListener.
         * PauseOnScrollListener(TaskHandler taskHandler, boolean pauseOnScroll,
         * boolean pauseOnFling)
         */
        imageListView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,false,true));

        imageListAdapter = new ImageListAdapter(this);
        imageListView.setAdapter(imageListAdapter);

        // 加载url请求返回的图片连接给listview
        // 这里只是简单的示例，并非最佳实践，图片较多时，最好上拉加载更多...
        for (String url : imgSites) {
            loadImgList(url);
        }

        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LruCacheActivity.this, ImageActivity.class);
                intent.putExtra("url",
                        imageListAdapter.getItem(position).toString());
                startActivity(intent);
            }
        });
        }

    /**
     * 进行网络加载 (HttpMethod method, String url, RequestCallBack<String> callBack)
     *
     */
    private void loadImgList(String url) {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // 得到的是已打算html内容
                Log.e("TAG", "" + responseInfo.result);
                // 通过正则来获取图片的url地址，并且添加到adapter中
                imageListAdapter.addSrc(getImgSrcList(responseInfo.result));
                // 通知listview更新数据
                imageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("TAG", "加载失败。。。");
            }
        });
    }
    /**
     * 得到网页中图片的地址 http://image.baidu.com/
     */
    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();
        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
            }
        }
        return pics;
    }
}
