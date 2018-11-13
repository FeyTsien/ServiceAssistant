package com.dt.serviceassistant.ui.fragment.analysis;

import com.dt.serviceassistant.bean.AnalysisBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public class AnalysisContract {

    public interface View extends BaseView {

        void requestSuccess(AnalysisBean analysisBean);

        void requestFail(String msg);
    }


    interface Presenter extends BasePresenter<View> {

        //请求接口
        void request(String url, String jsonData);

    }
}
