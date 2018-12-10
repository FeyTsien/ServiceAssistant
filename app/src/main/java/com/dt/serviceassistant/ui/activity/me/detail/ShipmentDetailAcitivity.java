package com.dt.serviceassistant.ui.activity.me.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MVPActivity;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.tsienlibrary.bean.CommonBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShipmentDetailAcitivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    public static final String SHIPMENT_DATA_ITEM = "shipment_data_item";

    private MBean.MsgBean mDataBean;
    private String mUserid;
    private String mShipmentcompany;
    private String mReceivingCompany;
    private String mRtime;
    private String mContact;
    private String mContactPhone;
    private String mDescription;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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


    @Override
    protected void initData() {
        mDataBean = (MBean.MsgBean) getIntent().getSerializableExtra(SHIPMENT_DATA_ITEM);
        mUserid = mDataBean.getUserid();
        mShipmentcompany = mDataBean.getScompany();
        mReceivingCompany = mDataBean.getRcompany();
        mRtime = mDataBean.getRtime();
        mContact = mDataBean.getContact();
        mContactPhone = mDataBean.getPhone();
        mDescription = mDataBean.getDescription();
    }


    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, "发货详情");
        mTvShipmentDate.setText(mRtime);
        mTvShipmentCompany.setText(mShipmentcompany);
        mTvReceivingCompany.setText(mReceivingCompany);
        mTvContact.setText(mContact);
        mTvContactPhone.setText(mContactPhone);
        mTvInsuranceThat.setText(mDescription);
    }


    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
    }
}
