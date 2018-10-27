package com.dt.serviceassistant.ui.activity.insurancedetail;

import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InsuranceDetailContract {
    public interface View extends BaseView {

//        //检验账号未注册，不可登录，需先注册
//        void unregistered();
//        //检验账号已注册，可直接登录
//        void registered();

        void loginSuccess(AppBean appBean);

        void loginError(String error);

    }

    interface Presenter extends BasePresenter<View> {

//        //检验账号是否注册
//        void checkIsRegist(String username);
        //登录
        void login(String email, String password);
//        void login(String jsonData);

    }
}
