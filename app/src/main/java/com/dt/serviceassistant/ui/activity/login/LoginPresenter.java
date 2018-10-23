package com.dt.serviceassistant.ui.activity.login;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private static String TAG = "LoginPresenter";

    // 登录
    @Override
    public void login(String email, String password) {

        Map<String, String> map = new HashMap<>();
        map.put("phone", email);
//      String password = EncryptUtils.encryptMD5ToString(pwdOrCode);
        map.put("password", password);
        HttpManager.getHttpManager().postMethod(UrlUtils.LOGIN, new Observer<String>() {

            @Override
            public void onError(Throwable e) {
                mView.loginError();
                LogUtils.i(TAG, "onError:+ ++++++++++++++" + e.toString());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtils.i(TAG, "onNext:+ ++++++++++++++" + s);
                Gson gson = new Gson();
                AppBean appBean = gson.fromJson(s, AppBean.class);

                if (appBean.getStatus()==1) {
                    if (mView != null) {
                        mView.loginSuccess(appBean);
                    }
                } else {
                    if (mView != null) {
                        mView.loginError();
                    }
                    ToastUtils.showShort(appBean.getReason());
                }
            }
        }, map);

    }
}
