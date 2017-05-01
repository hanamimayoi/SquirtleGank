package com.hanami.squirtle.ui.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hanami.squirtle.app.MyApplication;
import com.hanami.squirtle.bean.GankEntity;
import com.hanami.squirtle.http.GankApi;
import com.hanami.squirtle.http.MyCallback;
import com.hanami.squirtle.ui.activity.GalleryActivity;
import com.hanami.squirtle.ui.iVew.IPublicView;
import com.hanami.squirtle.ui.presenter.IPublicPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanami on 2017/4/17.
 */

public class PublicPresenterImpl extends BasePresenterImpl<IPublicView> implements IPublicPresenter {

    private Context mContext;

    private List<GankEntity> gankList;

    private final int pageSize = 20;
    private int pageIndex = 1;

    public static final int REFRESH_DATA = 0x001;
    public static final int MORE_DATA = 0x002;

    private MyCallback mCallback = new MyCallback() {
        @Override
        public void onSuccess(int what, Object result) {

        }

        @Override
        public void onSuccessList(int what, List<GankEntity> results) {
            //如果没有绑定View，就返回
            if (mView == null) {
                return;
            }
            //如果返回的results为空，则结束加载，不管是刷新，还是加载更多
            if (results == null) {
                Log.d(MyApplication.LOG_TAG, "results == null ----------------------");
                mView.finishRefresh();
                return;
            }
            //初始化dataList
            if (gankList == null) {
                gankList = new ArrayList<>();
            }

            //判断是刷新，还是加载更多
            switch (what) {

                case REFRESH_DATA: //刷新
                    pageIndex = 1;
                    pageIndex++;
                    gankList.clear();
                    gankList.addAll(results);
                    mView.setGankList(gankList);
                    break;

                case MORE_DATA:
                    if (results.size() == 0) {
                        mView.showToast("没有更多啦~");
                    } else {
                        pageIndex++;
                        gankList.addAll(results);
                        mView.setGankList(gankList);
                    }
                    break;

            }
            mView.finishRefresh();
        }

        @Override
        public void onFail(int what, String result) {
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * PublicPresenterImpl 的构造函数
     *
     * @param context
     * @param view
     */
    public PublicPresenterImpl(Context context, IPublicView view) {
        this.mContext = context;
        attachView(view);
    }

    @Override
    public void getNewDatas() {
        GankApi.getCommonNewData("福利", pageSize, 1, REFRESH_DATA, mCallback);
    }

    @Override
    public void getMoreDatas() {
        GankApi.getCommonNewData("福利", pageSize, pageIndex, MORE_DATA, mCallback);
    }

    @Override
    public void clickItem(int size, int position) {
        Intent intent = new Intent(mContext, GalleryActivity.class);
        intent.putExtra("size", size);
        intent.putExtra("position", position);

        ArrayList<String> urls = new ArrayList<>();

        for (GankEntity e : gankList) {
            urls.add(e.getUrl());
        }

        intent.putStringArrayListExtra("urls", urls);
        mContext.startActivity(intent);
    }
}
