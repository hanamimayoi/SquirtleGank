package com.hanami.squirtle.ui.presenter.impl;

import android.content.Context;
import android.content.Intent;

import com.hanami.squirtle.ui.iVew.IGalleryView;
import com.hanami.squirtle.ui.presenter.IGalleryPresenter;

import java.util.List;

/**
 * Created by hanami on 2017/4/24.
 */

public class GalleryPresenterImpl extends BasePresenterImpl<IGalleryView> implements IGalleryPresenter {

    private Context mContext;

    private List<String> urls;

    public GalleryPresenterImpl(Context context, IGalleryView view) {

        this.mContext = context;
        attachView(view);
    }

    @Override
    public void getImageList(Intent intent) {
        urls = intent.getStringArrayListExtra("urls");

        if (urls != null) {
            mView.setImageList(urls);
        }
    }
}
