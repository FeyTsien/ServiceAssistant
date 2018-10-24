package com.dt.serviceassistant.ui.fragment.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPBaseFragment;


/**
 * ================
 * ===== 我的 =====
 * ================
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View {

    private String TAG = getClass().getSimpleName();

    private View mRootView;


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
        return mRootView;
    }
}
