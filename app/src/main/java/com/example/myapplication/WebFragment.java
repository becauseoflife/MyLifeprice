package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {


    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_web, container, false);
        WebView webView = view.findViewById(R.id.web_view);
        // 在当前视图打开
        webView.setWebViewClient(new WebViewClient());
        // 一般允许JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        // 加载URL
        webView.loadUrl("http://news.sina.cn/");

        return view;
    }

}
