package com.dt.serviceassistant.ui.fragment.insurance;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.ui.activity.insurancedetail.InsuranceDetailAcitivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ft.widget.MultiItemDivider;

/**
 * ================
 * ===== 保险 =====
 * ================
 */

public class InsuranceFragment extends MVPBaseFragment<MContract.View, MPresenter> implements MContract.View {

    private String TAG = getClass().getSimpleName();

    private String mUserid;
    private String mReceivingCompany;
    private String mContact;
    private String mContactPhone;
    private String mRtime;
    private String mDescription;

    private View mRootView;

    @BindView(R.id.et_insurance_date)
    EditText mEtInsuranceDate;
    @BindView(R.id.et_receiving_company)
    EditText mEtReceivingCompany;
    @BindView(R.id.et_contact)
    EditText mEtContact;
    @BindView(R.id.et_contact_phone)
    EditText mEtContactPhone;
    @BindView(R.id.et_insurance_that)
    EditText mEtInsuranceThat;

    public static InsuranceFragment newInstance() {
        return new InsuranceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_insurance, container, false);
        ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
    }

    private void initView() {
    }

    @OnClick(R.id.btn_submit)
    public void submitShipment() {
        mUserid= AppData.getUserId();
        mRtime = mEtInsuranceDate.getText().toString();
        mReceivingCompany = mEtReceivingCompany.getText().toString();
        mContact = mEtContact.getText().toString();
        mContactPhone = mEtContactPhone.getText().toString();
        mDescription = mEtInsuranceThat.getText().toString();

        MBean.DataBean dataBean = new MBean.DataBean();
        dataBean.setUserid(mUserid);
        dataBean.setRcompany(mReceivingCompany);
        dataBean.setRtime(mRtime);
        dataBean.setContact(mContact);
        dataBean.setPhone(mContactPhone);
        dataBean.setDescription(mDescription);
        Gson gson = new Gson();
        String jsonData = gson.toJson(dataBean);
        mPresenter.request(UrlUtils.ADD_INSURANCE, jsonData);
    }

    @Override
    public void requestSuccess(MBean mBean) {
        CommonUtils.showInfoDialog(getActivity(), "提交成功！", "提示", null, "确认", null, null);
    }

    @Override
    public void requestFail(String msg) {
        ToastUtils.showLong(msg);
    }

}
