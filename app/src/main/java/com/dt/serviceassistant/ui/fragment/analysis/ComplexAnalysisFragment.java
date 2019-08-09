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
import com.dt.serviceassistant.bean.ComplexAnalysisBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 综合分析
 */

public class ComplexAnalysisFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View{
    private String TAG = getClass().getSimpleName();


    private List<ComplexAnalysisBean.InventoryBean> mFlowAnalysisList;
    private List<ComplexAnalysisBean.CustomerMonthTonnage> mCustomerInventoryList;
    private  double totalTonnage = 1 ;
    private  double monthTonnage = 1 ;

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
    public static ComplexAnalysisFragment newInstance() {
        return new ComplexAnalysisFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complex_analysis;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mFlowAnalysisList = new ArrayList<>();
        mCustomerInventoryList = new ArrayList<>();

    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewFlow.setLayoutManager(layoutManager);
        mRecyclerViewInventory.setLayoutManager(layoutManager2);

        mAdapterFlow = new MyBaseAdapter<ComplexAnalysisBean.InventoryBean>(mFlowAnalysisList, R.layout.item_flow_analysis) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvCity = holder.getView(R.id.tv_city);
                TextView tvWeight = holder.getView(R.id.tv_weight);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);

                tvCity.setText(mFlowAnalysisList.get(position).getCity());
                tvWeight.setText(mFlowAnalysisList.get(position).getWeight().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "吨");
                progressBar.setMax(100);
                int progressValue = (int) (mFlowAnalysisList.get(position).getWeight().doubleValue() / monthTonnage * 100);
                if (progressValue > 100) {
                    progressValue = 100;
                }
                progressBar.setProgress(progressValue);
            }
        };

        mAdapterInventory = new MyBaseAdapter<ComplexAnalysisBean.CustomerMonthTonnage>(mCustomerInventoryList, R.layout.item_flow_analysis) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvCity = holder.getView(R.id.tv_city);
                TextView tvWeight = holder.getView(R.id.tv_weight);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);

                tvCity.setText(mCustomerInventoryList.get(position).getMonth());
                tvWeight.setText(mCustomerInventoryList.get(position).getTonnage().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "吨");
                progressBar.setMax(100);
                int progressValue = (int) (mCustomerInventoryList.get(position).getTonnage().doubleValue() / totalTonnage * 100);
                if (progressValue > 100) {
                    progressValue =  100;
                }
                progressBar.setProgress(progressValue);
            }
        };
        mRecyclerViewFlow.setAdapter(mAdapterFlow);
        mRecyclerViewInventory.setAdapter(mAdapterInventory);

        //触动下拉刷新
        smartRefreshLayout.autoRefresh();
        //刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });
    }

    /**
     * 请求
     */
    private void request() {
        mPresenter.request(UrlUtils.BOSS_COMPLEX_ANALYSIS, "", ComplexAnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        //关闭下拉
        smartRefreshLayout.finishRefresh();

        ComplexAnalysisBean complexAnalysisBean = (ComplexAnalysisBean) commonBean.getData();
        mFlowAnalysisList.clear();
        mFlowAnalysisList.addAll(complexAnalysisBean.getDirection_analysis());
        mCustomerInventoryList.clear();
        mCustomerInventoryList.addAll(complexAnalysisBean.getTonnage_analysis());

        mTvTotalInventory.setText(complexAnalysisBean.getTotalshipment() + "吨");
        mTvNewlyIncreased.setText(complexAnalysisBean.getMonthshipment() + "吨");
        totalTonnage = complexAnalysisBean.getTotalshipment();
        monthTonnage = complexAnalysisBean.getMonthshipment() ;
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
