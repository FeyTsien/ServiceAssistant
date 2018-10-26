package com.dt.serviceassistant.ui.activity.shipmentinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShipmentInfoAcitivity extends MVPBaseActivity<ShipmentInfoContract.View, ShipmentInfoPresenter> implements ShipmentInfoContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shipment_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mTvTitle.setText("发货明细");
    }

    @Override
    public void loginSuccess(AppBean appBean) {

    }

    @Override
    public void loginError(String error) {

    }
}
