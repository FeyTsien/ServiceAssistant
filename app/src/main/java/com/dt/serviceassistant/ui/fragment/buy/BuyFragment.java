package com.dt.serviceassistant.ui.fragment.buy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPBaseFragment;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BuyFragment extends MVPBaseFragment<BuyContract.View, BuyPresenter> implements BuyContract.View {

    private String TAG = getClass().getSimpleName();

    private View mRootView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_buy, container, false);
        return mRootView;
    }
}
