package com.dt.serviceassistant.ui.fragment.me;


import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.login.LoginPwdActivity;
import com.dt.serviceassistant.ui.activity.me.InsuranceListAcitivity;
import com.dt.serviceassistant.ui.activity.me.ShipmentListAcitivity;
import com.dt.serviceassistant.ui.activity.me.TaskListAcitivity;
import com.dt.serviceassistant.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================
 * ===== 我的 =====
 * ================
 */

public class MeFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View {

    private String TAG = getClass().getSimpleName();

    private View mRootView;

    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_task_list)
    TextView mTvTaskList;


    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        mTvUsername.setText(AppData.getPhoneNumber());

        if (TextUtils.equals(AppData.getRoleType(), "1")) {
            mTvTaskList.setVisibility(View.GONE);
        } else if (TextUtils.equals(AppData.getRoleType(), "3")) {
            mTvTaskList.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 任务列表
     */
    @OnClick(R.id.tv_task_list)
    public void goToTaskList() {
        ActivityUtils.startActivity(new Intent(getActivity(), TaskListAcitivity.class));
    }

    /**
     * 发货列表
     */
    @OnClick(R.id.tv_shipment_list)
    public void goToShipmentList() {
        ActivityUtils.startActivity(new Intent(getActivity(), ShipmentListAcitivity.class));
    }

    /**
     * 保险列表
     */
    @OnClick(R.id.tv_insurance_list)
    public void goToInsuranceList() {
        ActivityUtils.startActivity(new Intent(getActivity(), InsuranceListAcitivity.class));
    }

    @OnClick(R.id.btn_login_out)
    public void loginOut() {
        CommonUtils.showInfoDialog(getActivity(), "确定要退出登录吗？", "提示", "取消", "退出", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppData.setLogined(false);
                AppData.setUserId("");
                AppData.setRoleType("");
                AppData.setPhoneNumber("");
                AppData.setPassword("");
                getActivity().finish();
                ActivityUtils.startActivity( LoginPwdActivity.class);
            }
        });
    }


}
