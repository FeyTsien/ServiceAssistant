package com.dt.serviceassistant.ui.activity.me;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.mvp.MContract;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public class MePresenter extends BasePresenterImpl<MeContract.View> implements MeContract.Presenter {

    @Override
    public void request(final String url, String jsonData) {

        HttpManager.getHttpManager().postJson(url, jsonData, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jsons = responseBody.string();
                    Gson gson = new Gson();
                    MBean mBean = gson.fromJson(jsons, MBean.class);
                    if (mBean.getCode() == 1) {
                        if (mView != null) {
                            mView.requestSuccess(mBean,url);
                        }
                    } else {
                        if (mView != null) {
                            mView.requestFail(mBean.getMsg());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

                ToastUtils.showLong("Error:" + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });

    }

}
