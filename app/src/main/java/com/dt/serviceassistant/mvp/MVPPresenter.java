package com.dt.serviceassistant.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.mvp.model.HttpManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.base.BasePresenterImpl;

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
public class MVPPresenter extends BasePresenterImpl<MVPContract.View> implements MVPContract.Presenter {

    @Override
    public void request(final String requestUrl, String jsonData, final Class clazz) {

        HttpManager.getHttpManager().postJson(requestUrl, jsonData, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i("onSubscribe==========");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                LogUtils.i("onNext==========");
                try {
                    String jsons = responseBody.string();
                    CommonBean commonBean = CommonBean.fromJson(jsons, clazz);
                    if (commonBean.getCode() == 1) {
                        if (mView != null) {
                            mView.requestSuccess(requestUrl, commonBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.requestFail(requestUrl, commonBean.getMsg());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

                LogUtils.i("onError:" + requestUrl + "===" + e.getMessage());
                ToastUtils.showLong("Error:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.i("onComplete==========");
            }
        });

    }

    @Override
    public void setPermissions(RxPermissions rxPermissions, String... permissions) {

        rxPermissions
                .requestEachCombined(permissions)
                .subscribe(permission -> { // will emit 1 Permission object
                    if (permission.granted) {
                        // All permissions are granted !
//                        ToastUtils.showLong("所有请求的权限都被授予");
                        if (mView != null) {
                            mView.permissionsAreGranted();
                        }
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        ToastUtils.showLong("未经请求而被拒绝");
                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                        if (mView != null) {
                            mView.goToSettings();
                        }
                        ToastUtils.showLong("至少有一个人拒绝批准，但要求永远不再\n" +
                                "需要去设置");
                    }
                });


//        rxPermissions
//                .requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Consumer<Permission>() {
//                               @Override
//                               public void accept(Permission permission) throws Exception {
//                                   if (permission.granted) {
//                                   } else if (permission.shouldShowRequestPermissionRationale) {
//                                       // Denied permission without ask never again
//                                   } else {
//                                       // Denied permission with ask never again
//                                       // Need to go to the settings
//                                   }
//                               }
//                           }, new Consumer<Throwable>() {
//                               @Override
//                               public void accept(Throwable t) {
//                                   LogUtils.e(TAG, "onError", t);
//                               }
//                           },
//                        new Action() {
//                            @Override
//                            public void run() {
//                                LogUtils.i(TAG, "OnComplete");
//                            }
//                        });
    }
}
