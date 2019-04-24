package com.haste.lv.faith.uiviews.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.haste.lv.faith.utils.LogUtils;


/**
 * 用于JavaScript交互
 */
public class JavaScriptLog {

    private final String TAG = "JavaScriptLog";

    private Context context;

    private JavaScriptCallBack callBack = null;

    private ClickImageCallBack clickImageCallBack = null;

    private CheckStyleCallBack checkStyleCallBack = null;

    private ClickEditLinkCallBack clickEditLinkCallBack = null;

    public JavaScriptLog(Context context, JavaScriptCallBack callBack){
        this.callBack = callBack;
        this.context = context;
    }

    public JavaScriptLog(Context context, ClickImageCallBack callBack){
        this.clickImageCallBack = callBack;
        this.context = context;
    }

    /**
     * 样式识别回调
     * @param callBack
     */
    public void setCheckStyleCallBack(CheckStyleCallBack callBack){
        this.checkStyleCallBack = callBack;
    }

    /**
     * 点击图片回调
     * @param callBack
     */
    public void setClickImageCallBack(ClickImageCallBack callBack){
        this.clickImageCallBack = callBack;
    }

    /**
     * 编辑页面点击超链接回调
     * @param callBack
     */
    public void setClickEditLinkCallBack(ClickEditLinkCallBack callBack){
        this.clickEditLinkCallBack = callBack;
    }

    public JavaScriptLog(){
    }

    /**
     * 点击图片
     */
    public interface JavaScriptCallBack{
        public void clickWebView();
    }

    /**
     * 样式识别回调
     */
    public interface CheckStyleCallBack{
        public void checkStyle(String json);
    }

    /**
     * 点击图片
     */
    public interface ClickImageCallBack{
        public void clickImage(String src);
    }

    public interface ClickEditLinkCallBack{
        public void clickEditLink(String url, String title);
    }

    public JavaScriptLog(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void toast(String message) {
        Toast.makeText(context,"js:"+message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void log(String result) {
        LogUtils.v(TAG,"js:"+result);
    }

    /**
     * 点击富文本
     */
    @JavascriptInterface
    public void clickWebView() {
        if (callBack!=null){
            callBack.clickWebView();
        }
    }

    /**
     * 点击图片
     */
    @JavascriptInterface
    public void clickImage(String src){
        if (clickImageCallBack!=null){
            clickImageCallBack.clickImage(src);
        }
    }

    /**
     * 传递样式识别数据回来
     * @param json
     */
    @JavascriptInterface
    public void checkStyle(String json){
        if (checkStyleCallBack!=null){
            checkStyleCallBack.checkStyle(json);
        }
    }

    /**
     * 编辑时候点击超链接
     * @param link
     * @param title
     */
    @JavascriptInterface
    public void clickEditLink(String link, String title){
        if (clickEditLinkCallBack!=null){
            clickEditLinkCallBack.clickEditLink(link,title);
        }
    }

}
