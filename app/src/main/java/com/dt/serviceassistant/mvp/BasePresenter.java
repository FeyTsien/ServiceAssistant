package com.dt.serviceassistant.mvp;

/**
 * MVPPlugin
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}
