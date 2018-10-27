package com.dt.serviceassistant.ui.activity.shipmentdetail;

import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.BasePresenter;
import com.dt.serviceassistant.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ShipmentDetailContract {
    public interface View extends BaseView {

        void addShipmentSuccess(AppBean appBean);

        void addShipmentError(String error);

    }

    interface Presenter extends BasePresenter<View> {

        void addShipment(String jsonData);
    }
}
