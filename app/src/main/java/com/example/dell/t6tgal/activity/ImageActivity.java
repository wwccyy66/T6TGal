package com.example.dell.t6tgal.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

/**
 * Created by dell on 2017/3/15.
 */
public class ImageActivity extends Activity{
    private ImageView bigImage;
    private BitmapUtils bitmapUtils;
    private BitmapDisplayConfig bigPicDisplayConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        bigImage = (ImageView) findViewById(R.id.big_img);
        String imgUrl = getIntent().getStringExtra("url");
        if (bitmapUtils == null) {
            bitmapUtils = BitmapHelp.getBitmapUtils(this);
        }
        bigPicDisplayConfig = new BitmapDisplayConfig();
        // 显示原始图片,不压缩, 尽量不要使用, 图片太大时容易OOM。
        //bigPicDisplayConfig.setShowOriginal(true);
        bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
        bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(this));

        BitmapLoadCallBack<ImageView> callBack=new DefaultBitmapLoadCallBack<ImageView>(){
            @Override
            public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
                Toast.makeText(getApplicationContext(), uri, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);
                Toast.makeText(getApplicationContext(), bitmap.getWidth() + "*" + bitmap.getHeight(), Toast.LENGTH_LONG).show();
            }
        };
        bitmapUtils.display(bigImage,imgUrl,bigPicDisplayConfig,callBack);
    }
}
