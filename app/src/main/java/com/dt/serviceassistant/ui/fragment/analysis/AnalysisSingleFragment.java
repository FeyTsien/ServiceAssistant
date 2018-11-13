package com.dt.serviceassistant.ui.fragment.analysis;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AnalysisBean;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.DateUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ft.widget.MultiItemDivider;


/**
 */

public class AnalysisSingleFragment extends MVPBaseFragment<AnalysisContract.View, AnalysisPresenter> implements AnalysisContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private int mAnalysistype;

    private View mRootView;

    @BindView(R.id.tv_content)
    TextView mTvContent;

    public static AnalysisSingleFragment newInstance() {
        return new AnalysisSingleFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analysis_single, container, false);
        ButterKnife.bind(this, mRootView);
        initData();
        initView();
        return mRootView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
        request();
    }

    private void initView() {

    }

    /**
     * 请求
     */
    private void request() {

        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        appDataBean.setAnalysistype(mAnalysistype);
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.BOSS_ANALYSIS_SINGLE, jsonData);
    }

    @Override
    public void requestSuccess(AnalysisBean analysisBean) {
        mTvContent.setText(analysisBean.getData().getAnalysissingle());
    }

    @Override
    public void requestFail(String msg) {
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
