package com.hanami.squirtle.http;

import com.hanami.squirtle.bean.GankEntity;
import com.hanami.squirtle.bean.HttpResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hanami on 2017/4/14.
 */

public interface APIService {

    /**
     * 根据分类来获取数据
     *
     * @param type
     * @param count
     * @param page
     * @return
     */
    @GET("data/{type}/{count}/{page}")
    Call<HttpResult<List<GankEntity>>> getCommonNewData(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );


}
