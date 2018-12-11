package com.dt.serviceassistant.ui.fragment.shipments;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.DateUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.tsienlibrary.bean.CommonBean;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================
 * ===== 发货 =====
 * ================
 */

public class ShipmentsFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View {

    private String TAG = getClass().getSimpleName();

    private String mUserid;
    private String mShipmentCompany;
    private String mReceivingCompany;
    private String mContact;
    private String mContactPhone;
    private String mRtime;
    private String mDescription;

    private View mRootView;

    @BindView(R.id.et_shipment_company)
    EditText mEtShipmentCompany;
    @BindView(R.id.et_receiving_company)
    EditText mEtReceivingCompany;
    @BindView(R.id.tv_shipment_date)
    TextView mTvShipmentDate;
    @BindView(R.id.et_contact)
    EditText mEtContact;
    @BindView(R.id.et_contact_phone)
    EditText mEtContactPhone;
    @BindView(R.id.et_shipment_that)
    EditText mEtInsuranceThat;

    private TextView[] editTexts;
    private String[] hints = {"发货公司不能为空", "收货公司不能为空", "请选择发货日期", "联系人不能为空", "联系电话不能为空", "发货说明不能为空"};


    public static ShipmentsFragment newInstance() {
        return new ShipmentsFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shipment;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        editTexts = new TextView[]{mEtShipmentCompany, mEtReceivingCompany, mTvShipmentDate, mEtContact, mEtContactPhone, mEtInsuranceThat};
    }


    /**
     * 点击选择日期
     */
    @OnClick(R.id.tv_shipment_date)
    public void showDate() {
        Calendar calendar = Calendar.getInstance();
        DateUtils.showDatePickerDialog(getActivity(), 0, mTvShipmentDate, calendar);
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
        mShipmentCompany = mEtShipmentCompany.getText().toString();
        mReceivingCompany = mEtReceivingCompany.getText().toString();
        mRtime = mTvShipmentDate.getText().toString();
        mContact = mEtContact.getText().toString();
        mContactPhone = mEtContactPhone.getText().toString();
        mDescription = mEtInsuranceThat.getText().toString();

        MBean.MsgBean dataBean = new MBean.MsgBean();
        dataBean.setUserid(mUserid);
        dataBean.setScompany(mShipmentCompany);
        dataBean.setRcompany(mReceivingCompany);
        dataBean.setRtime(mRtime);
        dataBean.setContact(mContact);
        dataBean.setPhone(mContactPhone);
        dataBean.setDescription(mDescription);
        Gson gson = new Gson();
        String jsonData = gson.toJson(dataBean);
        mPresenter.request(UrlUtils.ADD_SHIPMENT, jsonData,MBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        CommonUtils.showInfoDialog(getActivity(), "提交成功！", "提示", null, "确认", null, null);

    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        ToastUtils.showLong(msg);
    }

}
