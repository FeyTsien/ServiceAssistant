package com.dt.serviceassistant.ui.activity.login;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter2 extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private static String TAG = "LoginPresenter";

    // 登录
    @Override
    public void login(String email, String password) {

        String data = "{ \"phone\":\"13911310425\" , \"password\":\"123456\" }";
        HttpManager.getHttpManager().postJson(UrlUtils.LOGIN, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Gson gson = new Gson();
                try {
                    String jsonData = response.body().string();

                    AppBean appBean = gson.fromJson(jsonData, AppBean.class);
                    if (appBean.getStatus() == 1) {
                        if (mView != null) {
                            mView.loginSuccess(appBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.loginError();
                        }
                        ToastUtils.showShort(appBean.getReason());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        }, data);
    }
}
