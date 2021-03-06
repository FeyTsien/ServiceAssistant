package com.dt.serviceassistant.ui.activity.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.MVPActivity;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.mvp.model.bean.UserBean;
import com.dt.serviceassistant.ui.activity.main.MainActivity;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.CountDownButton;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginPwdActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    private static String TAG = "LoginActivity";
    private String mPhoneNumber;
    private String mCode;

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_errors)
    TextView mTvErrors;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_pwd;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }


    /**
     * TODO:登录
     */
    @OnClick(R.id.btn_login)
    void login() {
        request();
    }

    ProgressDialog loginDialog;

    private void request() {
        loginDialog = CommonUtils.showProgressDialog(this, "正在登录");
        mPhoneNumber = mEtPhone.getText().toString();
        mCode = mEtPassword.getText().toString();
        if (!TextUtils.isEmpty(mPhoneNumber) && !TextUtils.isEmpty(mCode)) {
            AppBean.DataBean appDataBean = new AppBean.DataBean();
            appDataBean.setPhone(mPhoneNumber);
            appDataBean.setPassword(mCode);
            Gson gson = new Gson();
            String jsonData = gson.toJson(appDataBean);
            mPresenter.request(UrlUtils.LOGIN, jsonData, UserBean.class);
        } else {
            requestFail(UrlUtils.LOGIN, "账号密码不正确");
        }
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

        loginDialog.dismiss();
        UserBean userBean = (UserBean) commonBean.getData();
        AppData.setLogined(true);
        AppData.setUserId(userBean.getUserid());
        AppData.setRoleType(userBean.getRoletype());
        AppData.setPhoneNumber(mPhoneNumber);

        Intent intent;
        if (TextUtils.equals(AppData.getRoleType(), "1")) {
            //客户
            intent = new Intent(this, MainActivity.class);
        } else if (TextUtils.equals(AppData.getRoleType(), "2")) {
            //老板
            intent = new Intent(this, MainBossActivity.class);
        } else if (TextUtils.equals(AppData.getRoleType(), "3")) {
            //员工
            intent = new Intent(this, MainActivity.class);
        } else {
            //RoleType不是1,2,3。不是正常角色。不允许进入APP
            CommonUtils.showInfoDialog(this, "账号角色异常，请联系管理员。", "提示", "知道了", null, null, null);
            return;
        }
        startActivity(intent);

        finish();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        loginDialog.dismiss();
        mTvErrors.setVisibility(View.VISIBLE);
        mTvErrors.setText(msg);
        LogUtils.i(TAG, msg);
    }

}