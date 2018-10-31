package com.dt.serviceassistant.ui.activity.shipmentdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseActivity;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShipmentDetailAcitivity extends MVPBaseActivity<MContract.View, MPresenter> implements MContract.View {

    public static final String SHIPMENT_DATA_ITEM = "shipment_data_item";

    private MBean.DataBean mDataBean;
    private String mUserid;
    private String mShipmentcompany;
    private String mReceivingCompany;
    private String mRtime;
    private String mContact;
    private String mContactPhone;
    private String mDescription;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_shipment_company)
    TextView mTvShipmentCompany;
    @BindView(R.id.tv_receiving_company)
    TextView mTvReceivingCompany;
    @BindView(R.id.tv_shipment_date)
    TextView mTvShipmentDate;
    @BindView(R.id.tv_contact)
    TextView mTvContact;
    @BindView(R.id.tv_contact_phone)
    TextView mTvContactPhone;
    @BindView(R.id.tv_shipment_that)
    TextView mTvInsuranceThat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shipment_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mDataBean = (MBean.DataBean) getIntent().getSerializableExtra(SHIPMENT_DATA_ITEM);
        mUserid = mDataBean.getUserid();
        mShipmentcompany = mDataBean.getScompany();
        mReceivingCompany = mDataBean.getRcompany();
        mRtime = mDataBean.getRtime();
        mContact = mDataBean.getContact();
        mContactPhone = mDataBean.getPhone();
        mDescription = mDataBean.getDescription();
    }

    private void initView() {
        mTvTitle.setText("保险明细");
        mTvShipmentDate.setText(mRtime);
        mTvShipmentCompany.setText(mShipmentcompany);
        mTvReceivingCompany.setText(mReceivingCompany);
        mTvContact.setText(mContact);
        mTvContactPhone.setText(mContactPhone);
        mTvInsuranceThat.setText(mDescription);
    }


    @Override
    public void requestSuccess(MBean mBean) {
    }

    @Override
    public void requestFail(String msg) {
    }
}
