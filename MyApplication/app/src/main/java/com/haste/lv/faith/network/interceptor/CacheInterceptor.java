package com.haste.lv.faith.network.interceptor;

import android.content.Context;

import com.haste.lv.faith.utils.NetworkUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lv on 18-12-11.
 * 缓存拦截器，当没有网络连接的时候自动读取缓存中的数据，缓存存放时间默认为3天
 * <p>
 * 构建okhttp时加入
 * OkHttpClient okHttpClient = new OkHttpClient.Builder()
 * .cache(cache)
 * .addInterceptor(new CacheInterceptor(mContext))
 * .build();
 */
public class CacheInterceptor implements Interceptor {
    private Context context;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            // read from cache for 60 s
            int maxAge = 60;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            //读取缓存信息
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cache times is 3 days
            int maxStale = 60 * 60 * 24 * 3;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
