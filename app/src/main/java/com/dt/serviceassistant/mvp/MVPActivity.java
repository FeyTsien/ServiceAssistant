package com.dt.serviceassistant.mvp;


import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;
import com.tsienlibrary.mvp.base.MVPBaseActivity;

public abstract class MVPActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseActivity<V, T> implements MVPContract.View {

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestFail(String requestUrl, String msg) {

    }

    @Override
    public void permissionsAreGranted() {

    }

    @Override
    public void goToSettings() {

    }

}
