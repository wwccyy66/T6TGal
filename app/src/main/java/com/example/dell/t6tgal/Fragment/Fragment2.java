package com.example.dell.t6tgal.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.activity.NewListActivity;
import com.example.dell.t6tgal.activity.ViewPagerActivity;

import java.net.URL;

/**
 * Created by dell on 2017/1/10.
 */

public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view2,container,false);
        return v;
    }
}
