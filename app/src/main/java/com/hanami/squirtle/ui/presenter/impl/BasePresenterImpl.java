package com.hanami.squirtle.ui.presenter.impl;

import com.hanami.squirtle.ui.presenter.IBasePresenter;

/**
 * Created by hanami on 2017/4/17.
 */

public class BasePresenterImpl<T> implements IBasePresenter {

    public T mView;

    protected void attachView(T view) {
        this.mView = view;
    }


    @Override
    public void detachView() {
        this.mView = null;
    }
}
