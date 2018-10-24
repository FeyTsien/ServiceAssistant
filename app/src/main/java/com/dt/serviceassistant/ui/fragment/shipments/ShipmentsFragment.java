package com.dt.serviceassistant.ui.fragment.shipments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPBaseFragment;


/**
 * ================
 * ===== 发货 =====
 * ================
 */

public class ShipmentsFragment extends MVPBaseFragment<ShipmentsContract.View, ShipmentsPresenter> implements ShipmentsContract.View {

    private String TAG = getClass().getSimpleName();

    private View mRootView;


    public static ShipmentsFragment newInstance() {
        return new ShipmentsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_shipments, container, false);
        return mRootView;
    }
}
