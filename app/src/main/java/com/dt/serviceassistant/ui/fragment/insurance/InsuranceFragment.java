package com.dt.serviceassistant.ui.fragment.insurance;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.DateUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.tv_insurance_date)
    TextView mTvInsuranceDate;
    @BindView(R.id.et_receiving_company)
    EditText mEtReceivingCompany;
    @BindView(R.id.et_contact)
    EditText mEtContact;
    @BindView(R.id.et_contact_phone)
    EditText mEtContactPhone;
    @BindView(R.id.et_insurance_that)
    EditText mEtInsuranceThat;

    private TextView[] editTexts;
    private String[] hints = {"请选择保险日期", "收货公司不能为空", "联系人不能为空", "联系电话不能为空", "保险说明不能为空"};

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
        editTexts = new TextView[]{mTvInsuranceDate, mEtReceivingCompany, mEtContact, mEtContactPhone, mEtInsuranceThat};
    }


    /**
     * 点击选择日期
     */
    @OnClick(R.id.tv_insurance_date)
    public void showDate() {
        Calendar calendar = Calendar.getInstance();
        DateUtils.showDateAndTimePickerDialog(getActivity(), 0, mTvInsuranceDate, calendar);
    }

    /**
     * 点击提交
     */
    @OnClick(R.id.btn_submit)
    public void submitShipment() {
        if (editTexts.length == hints.length) {

            for (int i = 0; i < editTexts.length; i++) {
                if (TextUtils.isEmpty(editTexts[i].getText().toString())) {
                    CommonUtils.showInfoDialog(getActivity(), hints[i], "提示", null, "确认", null, null);
                    return;
                }
            }
        }

        mUserid = AppData.getUserId();
        mRtime = mTvInsuranceDate.getText().toString();
        mReceivingCompany = mEtReceivingCompany.getText().toString();
        mContact = mEtContact.getText().toString();
        mContactPhone = mEtContactPhone.getText().toString();
        mDescription = mEtInsuranceThat.getText().toString();

        MBean.DataBean.MsgBean dataBean = new MBean.DataBean.MsgBean();
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
