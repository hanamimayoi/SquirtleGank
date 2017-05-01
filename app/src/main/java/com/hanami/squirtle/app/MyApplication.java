package com.hanami.squirtle.app;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.hanami.squirtle.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hanami on 2017/4/14.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    private static Handler mHandler;

    public static final String LOG_TAG = "Test";

    public static final String MAP_ACCESS_KEY = "XTaz30QsUBNQYfwa4l8I11e6hFYTHcHN";

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mHandler = new Handler();
    }

    //对于需要Context上下文的地方，可以返回MyApplication对象
    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static OkHttpClient defaultOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);

        //设置 10M 大小的缓存
        File httpCacheDir = new File(mInstance.getCacheDir(), "okHttpCache");
        Cache cache = new Cache(httpCacheDir, 10 * 1024 * 1024);
        builder.cache(cache)
                .addInterceptor(OFFLINE_INTERCEPTOR)
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);

        return builder.build();

    }

    private static final Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Log.d(LOG_TAG, "application interceptor");

            Request originalRequest = chain.request();

            if (!NetworkUtils.isOnline()) {
                Log.d(LOG_TAG, "重写 request");

                int maxStale = 60 * 60 * 24 * 7; // 过期之后还可以超时一周
                originalRequest = originalRequest.newBuilder()
                        .header("Cache-Control", "only-if-cached,max-stale=" + maxStale)
                        .build();
            }

            return chain.proceed(originalRequest);
        }
    };

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Log.d(LOG_TAG, "network interceptor");

            Response originalResponse = chain.proceed(chain.request());

            String cacheControl = originalResponse.header("Cache-Control");

            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache")
                    || cacheControl.contains("Pragma")
                    || cacheControl.contains("must-validate")
                    || cacheControl.contains("max-age=0")) {

                Log.d(LOG_TAG, "重写 response");

                originalResponse = originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public,max-age=" + 10)
                        .build();

            }

            return originalResponse;

        }
    };
}
