package com.hanami.squirtle.http;

import com.hanami.squirtle.bean.GankEntity;

import java.util.List;

/**
 * Created by hanami on 2017/4/16.
 */

public interface MyCallback {
    /**
     * 成功的回调
     */
    void onSuccess(int what, Object result);

    /**
     * 成功的回调集合
     */
    void onSuccessList(int what, List<GankEntity> results);

    /**
     * 失败的回调
     */
    void onFail(int what, String result);

}
