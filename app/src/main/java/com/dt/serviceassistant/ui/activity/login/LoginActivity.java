package com.dt.serviceassistant.ui.activity.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.dt.serviceassistant.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ft.widget.CountDownButton;

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {


    public static LoginActivity instance;
    private static String TAG = "LoginActivity";
    private String mPhoneNumber;
    private String mCode;

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

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

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
        mTvTitle.setText("登录");
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

    ProgressDialog loginDialog;

    /**
     * TODO:登录
     */
    @OnClick(R.id.btn_login)
    void login() {

        loginDialog = CommonUtils.showProgressDialog(this, "正在登录");
        if (!NetworkUtils.isConnected()) {
            loginDialog.dismiss();
            CommonUtils.showInfoDialog(this, "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        mPhoneNumber = mEtPhone.getText().toString();
        mCode = mEtCode.getText().toString();
        if (!TextUtils.isEmpty(mPhoneNumber) && !TextUtils.isEmpty(mCode)) {
            mPresenter.login(mPhoneNumber, mCode);
        } else {
            loginDialog.dismiss();
            mTvErrors.setVisibility(View.VISIBLE);
            mTvErrors.setText(R.string.code_error);
        }

    }




    @Override
    public void loginSuccess(AppBean appBean) {
        loginDialog.dismiss();
        ToastUtils.showShort("登录成功");
//        //清空之前登录信息
//        AppData.clearLogin();
//        //保存最新登录信息
//        AppData.saveLogin(appBean);
        AppData.setPhoneNumber(mPhoneNumber);
//        AppData.setPassword(password);
        AppData.setLogined(true);

        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("appbean", appBean);
        startActivity(intent);

        finish();
    }

    @Override
    public void loginError() {
        loginDialog.dismiss();
        LogUtils.i(TAG, "登录故障");
    }

}