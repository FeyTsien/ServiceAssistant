package com.dt.serviceassistant.ui.activity.insurancedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dt.serviceassistant.R;
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

public class InsuranceDetailAcitivity extends MVPBaseActivity<MContract.View, MPresenter> implements MContract.View {

    public static final String INSURANCE_DATA_ITEM = "insurance_data_item";

    private MBean.DataBean mDataBean;
    private String mUserid;
    private String mRcompany;
    private String mContact;
    private String mPhone;
    private String mRtime;
    private String mDescription;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
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

    private void initData(){

        mDataBean = (MBean.DataBean) getIntent().getSerializableExtra(INSURANCE_DATA_ITEM);
        mUserid = mDataBean.getUserid();
        mRcompany = mDataBean.getRcompany();
        mRtime = mDataBean.getRtime();
        mContact = mDataBean.getContact();
        mPhone = mDataBean.getPhone();
        mDescription = mDataBean.getDescription();
    }
    private void initView(){
        mTvTitle.setText("保险明细");
    }

    @OnClick(R.id.btn_submit)
    public void submitShipment() {

        MBean.DataBean dataBean = new MBean.DataBean();
        dataBean.setUserid(mUserid);
        dataBean.setRcompany(mRcompany);
        dataBean.setRtime(mDataBean.getRtime());
        dataBean.setContact(mDataBean.getContact());
        dataBean.setPhone(mDataBean.getPhone());
        dataBean.setDescription(mDataBean.getDescription());
        Gson gson = new Gson();
        String jsonData = gson.toJson(dataBean);
        mPresenter.request(UrlUtils.ADD_INSURANCE, jsonData);
    }

    @Override
    public void requestSuccess(MBean mBean) {

    }

    @Override
    public void requestFail(String msg) {

    }
}
