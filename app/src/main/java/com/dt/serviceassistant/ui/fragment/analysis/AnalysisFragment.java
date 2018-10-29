package com.dt.serviceassistant.ui.fragment.analysis;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPBaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 */

public class AnalysisFragment extends MVPBaseFragment<AnalysisContract.View, AnalysisPresenter> implements AnalysisContract.View{
    private String TAG = getClass().getSimpleName();

    private ArrayList<String> listData;

    private View mRootView;

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
    }

    private void initView() {

    }

    /**
     * 初始化数据
     */
    private void initData() {

        listData = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            listData.add("item" + i);
        }
    }
}
