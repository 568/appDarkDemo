package com.haste.lv.faith.ui.maintab.childs;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.mvpvm.base.BaseAbsLifecycleFragment;

/**
 * Created by lv on 18-12-13.
 * 关于我
 */

public class AboutFragment extends BaseAbsLifecycleFragment {
    private WebView m_webview;
    private WebSettings webSetting;
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_content_layout, container, false);
        m_webview =  view.findViewById(R.id.webbrowser);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("关于我");
        }
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initWebView(m_webview);
        m_webview.loadUrl("file:///android_asset/about.html");
    }
    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(final WebView webView) {
        if (webView == null) {
            throw new RuntimeException(" WebView object is not null,plase invoke initWebView() !!!!");
        }
        m_webview = webView;
        webSetting = m_webview.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSetting.setLoadsImagesAutomatically(true);
        } else {
            webSetting.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 解决WebView不能加载http与https混合内容的问题
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");

        webSetting.setBuiltInZoomControls(true);
        webSetting.setSaveFormData(true);

        webSetting.setBlockNetworkImage(false);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 应用数据
        webSetting.setAllowFileAccess(true);
        m_webview.setVerticalScrollBarEnabled(false);
        m_webview.setVerticalScrollbarOverlay(false);
        m_webview.setHorizontalScrollBarEnabled(false);
        m_webview.setHorizontalScrollbarOverlay(false);

        webSetting.setSupportZoom(false);

        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setLoadWithOverviewMode(true);

        //设置WebView使用广泛的视窗
        webSetting.setUseWideViewPort(true);
        //设置WebView的用户代理字符串。如果字符串“ua”是null或空,它将使用系统默认的用户代理字符串
        //注：这里设置后部分手机会加载不出外链的页面
//        webSetting.setUserAgentString(getString(R.string.app_name));
        //支持手势缩放
        webSetting.setBuiltInZoomControls(true);
        //自动打开窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // 排版适应屏幕
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项

        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        //Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
        webView.getSettings().setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        webView.getSettings().setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        //Log.i("databasepath", webView.getSettings().getDatabasePath());

        //其他
        //setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
        //setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
        //setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
        //setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
        // setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
        // setSupportZoom 设置是否支持变焦
    }
    @Override
    public void loadData(long id) {

    }
}
