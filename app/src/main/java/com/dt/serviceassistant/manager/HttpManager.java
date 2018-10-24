package com.dt.serviceassistant.manager;


import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.interfaces.ProjectAPI;
import com.dt.serviceassistant.utils.RetrofitUtil;
import com.dt.serviceassistant.utils.UrlUtils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * w网络请求的工具类
 */

public class HttpManager {

    private String TAG = "HttpManager";

    public static HttpManager httpManager = new HttpManager();

    private HttpManager() {

    }


    public static HttpManager getHttpManager() {
        return httpManager;
    }


    /**
     * get方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void getMethod(String url, Observer<String> observer) {
        //获取被观察者
        Observable<String> observable = RetrofitUtil.getInstance().get(ProjectAPI.class).getMethod(url);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * Post方式请求（表单）
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void postMethod(String url, Observer<String> observer, Map map) {

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLong("网络崩溃了");
            return;
        }
        Observable<String> observable = RetrofitUtil.getInstance().get1(ProjectAPI.class, UrlUtils.BASEURL).postMethod(url, map);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * Post方式请求(json)
     * 封装时，传递observer
     *
     * @param url
     */
    public void postJson(String url, Callback<ResponseBody> callback, String datas) {

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLong("网络崩溃了");
            return;
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), datas);//datas是json字符串
        Call<ResponseBody> call = RetrofitUtil.getInstance().get1(ProjectAPI.class, UrlUtils.BASEURL).postJson(url, body);
        call.enqueue(callback);

    }


    /**
     * Post方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void postMethod1(String url, Observer<String> observer, Map map) {

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLong("网络崩溃了");
            return;
        }
        Observable<String> observable = RetrofitUtil.getInstance().get(ProjectAPI.class).postMethod(url, map);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

}
