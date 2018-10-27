package com.dt.serviceassistant.ui.fragment.insurance;


import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class InsurancePresenter extends BasePresenterImpl<InsuranceContract.View> implements InsuranceContract.Presenter{

    @Override
    public void getInsuranceList(String userId) {
        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(userId);
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);

        HttpManager.getHttpManager().postJson(UrlUtils.GET_INSURANCE_LIST, jsonData, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() == null) {
                        ToastUtils.showLong("没有数据");
                        return;
                    }
                    String jsons = response.body().string();
                    Gson gson = new Gson();
                    MBean messageBean = gson.fromJson(jsons, MBean.class);
                    if (messageBean.getCode() == 1) {
                        if (mView != null) {
                            mView.getInsuranceListSuccess(messageBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.getInsuranceListFail(messageBean.getMsg());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
