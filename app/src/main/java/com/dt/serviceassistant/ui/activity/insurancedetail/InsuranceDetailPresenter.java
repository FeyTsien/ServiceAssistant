package com.dt.serviceassistant.ui.activity.insurancedetail;

import android.app.ProgressDialog;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InsuranceDetailPresenter extends BasePresenterImpl<InsuranceDetailContract.View> implements InsuranceDetailContract.Presenter {

    private static String TAG = "LoginPresenter";

    ProgressDialog loginDialog;

    // 登录
    @Override
    public void login(String mPhoneNumber, String mCode) {
        String jsonData = "";
        loginDialog = CommonUtils.showProgressDialog(mView.getContext(), "正在登录");
        if (!NetworkUtils.isConnected()) {
            loginDialog.dismiss();
            CommonUtils.showInfoDialog(mView.getContext(), "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        if (!TextUtils.isEmpty(mPhoneNumber) && !TextUtils.isEmpty(mCode)) {
            AppBean.DataBean appDataBean = new AppBean.DataBean();
            appDataBean.setPhone(mPhoneNumber);
            appDataBean.setPassword(mCode);
            Gson gson = new Gson();
            jsonData = gson.toJson(appDataBean);
        } else {
            loginDialog.dismiss();
            mView.loginError("账号密码不正确");
        }


        HttpManager.getHttpManager().postJson(UrlUtils.LOGIN, jsonData, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loginDialog.dismiss();
                try {
                    String jsons = response.body().string();
                    Gson gson = new Gson();
                    AppBean appBean = gson.fromJson(jsons, AppBean.class);
                    if (appBean.getCode() == 1) {
                        if (mView != null) {
                            mView.loginSuccess(appBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.loginError(appBean.getMsg());
                        }
//                        ToastUtils.showShort(appBean.getReason());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loginDialog.dismiss();
            }
        });
    }
}
