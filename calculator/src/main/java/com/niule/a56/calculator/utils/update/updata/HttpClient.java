package com.niule.a56.calculator.utils.update.updata;


import com.niule.a56.calculator.base.XApplication;
import com.niule.a56.calculator.utils.update.fileload.FileResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * 作者： Hokas
 * 时间： 2016/12/8
 * 类别：
 */

public class HttpClient {

    private static HttpClient mInstance;
    private Retrofit singleton;

    public static HttpClient getIns(String base_url) {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) mInstance = new HttpClient(base_url);
            }
        }
        return mInstance;
    }

    public HttpClient(String BASE_URL) {

        File cacheFile = new File(XApplication.getInstance().getCacheDir(), "android");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder().body(new FileResponseBody(originalResponse))
                                .build();
                    }
                })
                .cache(cache)
                .build();

        singleton = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    public <T> T createService(Class<T> clz) {
        return singleton.create(clz);
    }
}
