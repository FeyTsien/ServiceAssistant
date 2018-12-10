package com.dt.serviceassistant.mvp;


import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;
import com.tsienlibrary.mvp.base.MVPBaseFragment;

/**
 * MVP的View回调实现类
 * @param <V>
 * @param <T>
 */
public abstract class MVPFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseFragment<V, T> implements MVPContract.View {


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
