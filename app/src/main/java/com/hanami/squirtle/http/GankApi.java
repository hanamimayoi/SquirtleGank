package com.hanami.squirtle.http;

import android.util.Log;

import com.hanami.squirtle.app.MyApplication;
import com.hanami.squirtle.bean.GankEntity;
import com.hanami.squirtle.bean.HttpResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hanami on 2017/4/16.
 */

public class GankApi {


    private static final String GET_DATA_FAIL = "获取数据失败...";
    private static final String NET_FAIL = "网络出了点小故障...";

    /**
     * 获取新数据的公用的方法
     *
     * @param type
     * @param count
     * @param page
     * @param what       0x001 表示刷新数据，0x002 表示加载更多数据
     * @param myCallback
     * @return
     */
    public static Call<HttpResult<List<GankEntity>>> getCommonNewData(String type, int count, int page, final int what, final MyCallback myCallback) {

        Call<HttpResult<List<GankEntity>>> commonNewData = BuildApi.getApiService().getCommonNewData(type, count, page);

        commonNewData.enqueue(new Callback<HttpResult<List<GankEntity>>>() {

            //got response with any response code
            @Override
            public void onResponse(Call<HttpResult<List<GankEntity>>> call, Response<HttpResult<List<GankEntity>>> response) {

                if (response.isSuccessful()) {
                    HttpResult<List<GankEntity>> httpResult = response.body();
                    if (httpResult != null) {
                        Log.d(MyApplication.LOG_TAG, httpResult.toString());
                        if (!httpResult.isError()) {
                            myCallback.onSuccessList(what, httpResult.getResults());
                            Log.d(MyApplication.LOG_TAG, Thread.currentThread().getName());
                        } else {
                            myCallback.onFail(what, GET_DATA_FAIL);
                        }
                    } else {
                        myCallback.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                    myCallback.onFail(what, GET_DATA_FAIL);
                }

            }

            //no response
            @Override
            public void onFailure(Call<HttpResult<List<GankEntity>>> call, Throwable t) {
                myCallback.onFail(what, NET_FAIL);
                Log.d(MyApplication.LOG_TAG, "did not get response --------------- ");
            }
        });

        return commonNewData;

    }

}
