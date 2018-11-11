package com.dt.serviceassistant.ui.activity.insurancedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsuranceDetailAcitivity extends MVPBaseActivity<MContract.View, MPresenter> implements MContract.View {

    public static final String INSURANCE_DATA_ITEM = "insurance_data_item";

    private MBean.DataBean mDataBean;
    private String mUserid;
    private String mReceivingCompany;
    private String mContact;
    private String mContactPhone;
    private String mRtime;
    private String mDescription;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_insurance_date)
    TextView mTvInsuranceDate;
    @BindView(R.id.tv_receiving_company)
    TextView mTvReceivingCompany;
    @BindView(R.id.tv_contact)
    TextView mTvContact;
    @BindView(R.id.tv_contact_phone)
    TextView mTvContactPhone;
    @BindView(R.id.tv_insurance_that)
    TextView mTvInsuranceThat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insurance_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {

        mDataBean = (MBean.DataBean) getIntent().getSerializableExtra(INSURANCE_DATA_ITEM);
        mUserid = mDataBean.getUserid();
        mReceivingCompany = mDataBean.getRcompany();
        mRtime = mDataBean.getRtime();
        mContact = mDataBean.getContact();
        mContactPhone = mDataBean.getPhone();
        mDescription = mDataBean.getDescription();
    }

    private void initView() {
        mTvTitle.setText("保险明细");
        mTvInsuranceDate.setText(mRtime);
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
