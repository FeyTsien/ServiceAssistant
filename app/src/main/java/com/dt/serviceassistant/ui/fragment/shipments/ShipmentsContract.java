package com.dt.serviceassistant.ui.fragment.shipments;

import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShipmentsContract {
    interface View extends BaseView {

        void getShipmentListSuccess(MBean mBean);

        void getShipmentListFail(String error);
    }

    interface  Presenter extends BasePresenter<View> {

        void getShipmentList(String userId);
    }
}
