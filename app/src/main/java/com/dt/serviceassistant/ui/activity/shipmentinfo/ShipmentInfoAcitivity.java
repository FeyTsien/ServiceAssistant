package com.dt.serviceassistant.ui.activity.shipmentinfo;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.MVPBaseActivity;

public class ShipmentInfoAcitivity extends MVPBaseActivity<ShipmentInfoContract.View, ShipmentInfoPresenter> implements ShipmentInfoContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shipment_info;
    }

    @Override
    public void loginSuccess(AppBean appBean) {

    }

    @Override
    public void loginError(String error) {

    }
}
