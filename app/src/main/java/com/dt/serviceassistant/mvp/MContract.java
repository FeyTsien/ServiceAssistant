package com.dt.serviceassistant.mvp;

import com.dt.serviceassistant.bean.MBean;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public class MContract {

    public interface View extends BaseView {

        void requestSuccess(MBean mBean);

        void requestFail(String msg);
    }


    interface Presenter extends BasePresenter<View> {

        //请求接口
        void request(String url,String jsonData);

    }
}
