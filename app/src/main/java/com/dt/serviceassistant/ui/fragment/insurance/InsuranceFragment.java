package com.dt.serviceassistant.ui.fragment.insurance;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.ui.fragment.shipments.ShipmentsFragment;

/**
 * ================
 * ===== 保险 =====
 * ================
 */

public class InsuranceFragment extends MVPBaseFragment<InsuranceContract.View, InsurancePresenter> implements InsuranceContract.View {

    private String TAG = getClass().getSimpleName();

    private View mRootView;

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
        return mRootView;
    }
}
