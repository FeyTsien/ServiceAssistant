package com.dt.serviceassistant.ui.fragment.information;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.manager.HttpManager;
import com.dt.serviceassistant.mvp.BasePresenterImpl;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationPresenter extends BasePresenterImpl<InformationContract.View> implements InformationContract.Presenter {

    @Override
    public void getNews(String url,String jsonData) {
        HttpManager.getHttpManager().postJson(UrlUtils.GET_NEWS_LIST, jsonData, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    if (responseBody == null) {
                        ToastUtils.showLong("没有数据");
                        return;
                    }
                    String jsons = responseBody.string();
                    Gson gson = new Gson();
                    MessageBean messageBean = gson.fromJson(jsons, MessageBean.class);
                    if (messageBean.getCode() == 1) {
                        if (mView != null) {
                            mView.getNewsSuccess(messageBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.getNewsFail(messageBean.getMsg());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        HttpManager.getHttpManager().postJson(UrlUtils.GET_NEWS_LIST, jsonData, new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    if (response.body() == null) {
//                        ToastUtils.showLong("没有数据");
//                        return;
//                    }
//                    String jsons = response.body().string();
//                    Gson gson = new Gson();
//                    MessageBean messageBean = gson.fromJson(jsons, MessageBean.class);
//                    if (messageBean.getCode() == 1) {
//                        if (mView != null) {
//                            mView.getNewsSuccess(messageBean);
//                        }
//                    } else {
//                        if (mView != null) {
//                            mView.getNewsFail(messageBean.getMsg());
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
