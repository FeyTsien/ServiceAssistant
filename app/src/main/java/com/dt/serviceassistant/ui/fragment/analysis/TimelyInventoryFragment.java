package com.dt.serviceassistant.ui.fragment.analysis;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.TimelyInventoryBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 业务员分析
 */

public class TimelyInventoryFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View{
    private String TAG = getClass().getSimpleName();


    private List<TimelyInventoryBean.FlowAnalysisBean> mFlowAnalysisList;
    private List<TimelyInventoryBean.CustomerInventoryBean> mCustomerInventoryList;

    private View mRootView;
    private MyBaseAdapter mAdapterFlow;
    private MyBaseAdapter mAdapterInventory;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_total_inventory)
    TextView mTvTotalInventory;
    @BindView(R.id.tv_newly_increased)
    TextView mTvNewlyIncreased;
    @BindView(R.id.recycler_view_flow)
    RecyclerView mRecyclerViewFlow;
    @BindView(R.id.recycler_view_inventory)
    RecyclerView mRecyclerViewInventory;
    public static TimelyInventoryFragment newInstance() {
        return new TimelyInventoryFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_timely_inventory;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mFlowAnalysisList = new ArrayList<>();
        mCustomerInventoryList = new ArrayList<>();

//        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewFlow.setLayoutManager(layoutManager);
        mRecyclerViewInventory.setLayoutManager(layoutManager2);

        mAdapterFlow = new MyBaseAdapter<TimelyInventoryBean.FlowAnalysisBean>(mFlowAnalysisList, R.layout.item_flow_analysis) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvCity = holder.getView(R.id.tv_city);
                TextView tvWeight = holder.getView(R.id.tv_weight);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);

                tvCity.setText(mFlowAnalysisList.get(position).getCity());
                tvWeight.setText(mFlowAnalysisList.get(position).getWeight() + "吨");
                progressBar.setMax(100);
                int progressValue = (int) (mFlowAnalysisList.get(position).getWeight() / mFlowAnalysisList.get(0).getWeight() * 100);
                if (progressValue > 100) {
                    progressValue = 100;
                }
                progressBar.setProgress(progressValue);
            }
        };

        mAdapterInventory = new MyBaseAdapter<TimelyInventoryBean.CustomerInventoryBean>(mCustomerInventoryList, R.layout.item_customer_inventory) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvWeight = holder.getView(R.id.tv_weight);
                TextView tvdays = holder.getView(R.id.tv_days);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);

                tvName.setText(mCustomerInventoryList.get(position).getName());
                tvWeight.setText(mCustomerInventoryList.get(position).getWeight() + "吨");
                tvdays.setText(mCustomerInventoryList.get(position).getDays() + "天");
                progressBar.setMax(100);
                int progressValue = (int) (mCustomerInventoryList.get(position).getWeight() / mCustomerInventoryList.get(0).getWeight() * 100);
                if (progressValue > 100) {
                    progressValue =  100;
                }
                progressBar.setProgress(progressValue);
            }
        };
        mRecyclerViewFlow.setAdapter(mAdapterFlow);
        mRecyclerViewInventory.setAdapter(mAdapterInventory);

        //刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });
        request();
    }

    /**
     * 请求
     */
    private void request() {
//        AppBean.DataBean appDataBean = new AppBean.DataBean();
//        appDataBean.setUserid(AppData.getUserId());
//        appDataBean.setKeyword(mKeyword);
//        appDataBean.setStarttime(mStartTime);
//        appDataBean.setEndtime(mEndTime);
//        appDataBean.setAnalysistype(mAnalysistype);
//        appDataBean.setStart(mStart);
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(appDataBean);
//        mPresenter.request(UrlUtils.BOSS_ANALYSIS_LIST, jsonData, AnalysisBean.class);
        mPresenter.request("HYFDjiKf", "", TimelyInventoryBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        //关闭下拉
        smartRefreshLayout.finishRefresh();

        TimelyInventoryBean timelyInventoryBean = (TimelyInventoryBean) commonBean.getData();
        mFlowAnalysisList.clear();
        mFlowAnalysisList.addAll(timelyInventoryBean.getFlow_analysis());
        mCustomerInventoryList.clear();
        mCustomerInventoryList.addAll(timelyInventoryBean.getCustomer_inventory());

        mTvTotalInventory.setText(timelyInventoryBean.getTotal_inventory() + "吨");
        mTvNewlyIncreased.setText(timelyInventoryBean.getNewly_increased() + "吨");
        mAdapterFlow.notifyDataSetChanged();
        mAdapterInventory.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
        //关闭下拉
        smartRefreshLayout.finishRefresh();
        ToastUtils.showLong(msg);
    }

}
