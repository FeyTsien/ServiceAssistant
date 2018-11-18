package com.dt.serviceassistant.ui.activity.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.MVPBaseActivity;
import com.dt.serviceassistant.ui.activity.main.MainActivity;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.utils.CommonUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ft.widget.CountDownButton;

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {


    public static LoginActivity instance;
    private static String TAG = "LoginActivity";
    private String mPhoneNumber;
    private String mCode;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_errors)
    TextView mTvErrors;
    @BindView(R.id.btn_code)
    CountDownButton mCdbCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        //找控件
        initView();
    }

    private void initView() {
        setToolBar(mToolbar, mTvTitle, "登录");
        mCdbCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里判断是否倒计时结束，避免在倒计时时多次点击导致重复请求接口
                if (mCdbCode.isFinish()) {
                    //发送验证码请求成功后调用
                    mCdbCode.start();
                }
            }
        });
    }


    /**
     * TODO:登录
     */
    @OnClick(R.id.btn_login)
    void login() {
        mPhoneNumber = mEtPhone.getText().toString();
        mCode = mEtCode.getText().toString();
        mPresenter.login(mPhoneNumber, mCode);
    }


    @Override
    public void loginSuccess(AppBean appBean) {
        AppData.setLogined(true);
        AppData.setUserId(appBean.getData().getUserid());
        AppData.setRoleType(appBean.getData().getRoletype());
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
    public void loginError(String error) {
        mTvErrors.setVisibility(View.VISIBLE);
        mTvErrors.setText(error);
        LogUtils.i(TAG, error);
    }

}