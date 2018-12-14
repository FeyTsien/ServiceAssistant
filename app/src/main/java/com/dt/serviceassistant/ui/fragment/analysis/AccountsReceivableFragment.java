package com.dt.serviceassistant.ui.fragment.analysis;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AnalysisBean;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 */

public class AccountsReceivableFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View {
    private String TAG = getClass().getSimpleName();
    private int mAnalysistype;
    private List<AnalysisBean.RankingBean> mDataBeanList;

    private MyBaseAdapter mAdapter;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.tv_total_shipment)
    TextView mTvTotalShipment;
    @BindView(R.id.tv_paid)
    TextView mTvPaid;
    @BindView(R.id.tv_unpaid)
    TextView mTvUnpaid;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static AccountsReceivableFragment newInstance() {
        return new AccountsReceivableFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_accounts_receivable;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
        mDataBeanList = new ArrayList<>();
        request();
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyBaseAdapter<AnalysisBean.RankingBean>(mDataBeanList, R.layout.item_rank) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                TextView tvRank = holder.getView(R.id.tv_rank);
                if (position == 0) {
                    tvRank.setTextColor(0xFFF51D1D);
                } else if (position == 1) {
                    tvRank.setTextColor(0xFFF5691D);
                } else if (position == 2) {
                    tvRank.setTextColor(0xFFF5CF1D);
                }
                tvRank.setText("NO." + (position + 1));

                holder.setTextView(R.id.tv_client, mDataBeanList.get(position).getClient());
                holder.setTextView(R.id.tv_money, mDataBeanList.get(position).getMoney());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
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

        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        appDataBean.setAnalysistype(mAnalysistype);
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.BOSS_ANALYSIS_SINGLE, jsonData, AnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        //关闭下拉
        smartRefreshLayout.finishRefresh();
        AnalysisBean analysisBean = (AnalysisBean) commonBean.getData();
        mTvUpdateTime.setText(analysisBean.getUpdatetime());
        mTvAmount.setText(analysisBean.getAmount());
        mTvTotalShipment.setText(analysisBean.getTotalshipment());
        mTvPaid.setText(analysisBean.getPaid());
        mTvUnpaid.setText(analysisBean.getUnpaid());
        mDataBeanList.clear();
        mDataBeanList.addAll(analysisBean.getRanking());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
    }

}
