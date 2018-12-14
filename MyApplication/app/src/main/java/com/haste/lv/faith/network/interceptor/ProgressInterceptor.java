package com.haste.lv.faith.network.interceptor;

import com.haste.lv.faith.network.download.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by lv on 18-12-11.
 * 文件下载的进度监听回调
 */
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}
