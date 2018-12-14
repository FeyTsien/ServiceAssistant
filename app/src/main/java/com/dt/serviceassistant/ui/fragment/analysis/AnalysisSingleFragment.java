package com.dt.serviceassistant.ui.fragment.analysis;


import android.view.View;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AnalysisBean;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tsienlibrary.bean.CommonBean;

import butterknife.BindView;


/**
 */

public class AnalysisSingleFragment extends MVPFragment<MVPContract.View,MVPPresenter> implements MVPContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private int mAnalysistype;

    @BindView(R.id.tv_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;

    public static AnalysisSingleFragment newInstance() {
        return new AnalysisSingleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_analysis_single;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
        request();
    }
    @Override
    protected void initView() {

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
        mPresenter.request(UrlUtils.BOSS_ANALYSIS_SINGLE, jsonData,AnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        AnalysisBean analysisBean = (AnalysisBean) commonBean.getData();
        mTvUpdateTime.setText(analysisBean.getUpdatetime());
        mTvAmount.setText(analysisBean.getAmount());
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
