package com.hanami.squirtle.http;

import com.google.gson.GsonBuilder;
import com.hanami.squirtle.app.MyApplication;
import com.hanami.squirtle.constants.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hanami on 2017/4/16.
 */

public class BuildApi {

    private static Retrofit retrofit;

    public static APIService getApiService() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create()))//设置默认解析库为Gson
                    .client(MyApplication.defaultOkHttpClient())
                    .build();
        }

        return retrofit.create(APIService.class);

    }
}
