package com.dt.serviceassistant.ui.fragment.insurance;


import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class InsuranceContract {
    interface View extends BaseView {

        void getInsuranceListSuccess(MBean mBean);
        void getInsuranceListFail(String msg);
    }

    interface  Presenter extends BasePresenter<View> {

        void getInsuranceList(String userId);
    }
}
