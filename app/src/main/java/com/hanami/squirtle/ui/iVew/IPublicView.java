package com.hanami.squirtle.ui.iVew;

import com.hanami.squirtle.bean.GankEntity;

import java.util.List;

/**
 * Created by hanami on 2017/4/17.
 */

public interface IPublicView {

    void setGankList(List<GankEntity> results);

    void finishRefresh();

    void showToast(String msg);

    void showSnackBar();

}
