package com.dt.serviceassistant.ui.activity.me;

import com.dt.serviceassistant.bean.CodeBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

public class MeContract {

    public interface View extends BaseView {

        void requestSuccess(MBean mBean,String requestUrl);

        void requestFail(String msg);

    }


    interface Presenter extends BasePresenter<View> {

        //请求接口
        void request(String url,String jsonData);

    }
}