package com.dt.serviceassistant.ui.fragment.me;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.ui.activity.insurancelist.InsuranceListAcitivity;
import com.dt.serviceassistant.ui.activity.login.LoginActivity;
import com.dt.serviceassistant.ui.activity.shipmentlist.ShipmentListAcitivity;
import com.dt.serviceassistant.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * ================
 * ===== 我的 =====
 * ================
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View {

    private String TAG = getClass().getSimpleName();

    private View mRootView;

    @BindView(R.id.tv_username)
    TextView mTvUsername;


    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
    }

    private void initView() {
        mTvUsername.setText(AppData.getPhoneNumber());
    }

    @OnClick(R.id.tv_shipment_list)
    public void goToShipmentList() {
        ActivityUtils.startActivity(new Intent(getActivity(), ShipmentListAcitivity.class));
    }

    @OnClick(R.id.tv_insurance_list)
    public void goToInsuranceList() {
        ActivityUtils.startActivity(new Intent(getActivity(), InsuranceListAcitivity.class));
    }

    @OnClick(R.id.btn_login_out)
    public void loginOut() {
        CommonUtils.showInfoDialog(getActivity(), "网络不给力，请检查网络设置。", "提示", "取消", "退出", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppData.setLogined(false);
                AppData.setUserId("");
                AppData.setPhoneNumber("");
                AppData.setPassword("");
                getActivity().finish();
                ActivityUtils.startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }


}
