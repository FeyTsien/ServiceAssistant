package com.dt.serviceassistant.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public class MPresenter extends BasePresenterImpl<MContract.View> implements MContract.Presenter {

    @Override
    public void request(String url, String jsonData) {

        HttpManager.getHttpManager().postJson(url, jsonData, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i("onSubscribe==========");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                LogUtils.i("onNext==========");
                try {
                    String jsons = responseBody.string();
                    Gson gson = new Gson();
                    MBean mBean = gson.fromJson(jsons, MBean.class);
                    if (mBean.getCode() == 1) {
                        if (mView != null) {
                            mView.requestSuccess(mBean);
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

                LogUtils.i("onError==========");
                ToastUtils.showLong("Error:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.i("onComplete==========");
            }
        });


//        HttpManager.getHttpManager().postJson(url, jsonData, new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    if (response.body() == null) {
//                        ToastUtils.showLong("没有数据");
//                        return;
//                    }
//                    String jsons = response.body().string();
//                    Gson gson = new Gson();
//                    MBean mBean = gson.fromJson(jsons, MBean.class);
//                    if (mBean.getCode() == 1) {
//                        if (mView != null) {
//                            mView.requestSuccess(mBean);
//                        }
//                    } else {
//                        if (mView != null) {
//                            mView.requestFail(mBean.getMsg());
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

    }
}
