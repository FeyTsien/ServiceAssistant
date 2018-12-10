package com.dt.serviceassistant.mvp;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.base.BasePresenter;
import com.tsienlibrary.mvp.base.BaseView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public interface MVPContract {

    interface View extends BaseView {

        //请求成功后调用
        void requestSuccess(String requestUrl, CommonBean commonBean);

        //请求失败后调用
        void requestFail(String requestUrl, String msg);

        //所以权限已授予
        void permissionsAreGranted();

        //至少有一个人拒绝了“永远不要再问”的许可,需要去设置
        void goToSettings();
    }


    interface Presenter extends BasePresenter<View> {

        //请求接口
        void request(String requestUrl, String jsonData, Class clazz);

        //设置应用权限
        void setPermissions(RxPermissions rxPermissions, String... permissions);

    }
}
