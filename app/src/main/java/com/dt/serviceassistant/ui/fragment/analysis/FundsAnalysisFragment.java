package com.dt.serviceassistant.ui.fragment.analysis;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.CustomerAnalysisBean;
import com.dt.serviceassistant.bean.FundsAnalysisBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


/**
 * 资金分析
 */

public class FundsAnalysisFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View {
    private String TAG = getClass().getSimpleName();
    private int mAnalysistype;
    private List<FundsAnalysisBean.Funds> mDataBeanList;

    private MyBaseAdapter mAdapter;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    public static FundsAnalysisFragment newInstance() {
        return new FundsAnalysisFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_funds_analysis;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
        mDataBeanList = new ArrayList<>();
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyBaseAdapter<FundsAnalysisBean.Funds>(mDataBeanList, R.layout.item_funds) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
//                TextView tvRank = holder.getView(R.id.tv_rank);
//                if (position == 0) {
//                    tvRank.setTextColor(0xFFF51D1D);
//                } else if (position == 1) {
//                    tvRank.setTextColor(0xFFF5691D);
//                } else if (position == 2) {
//                    tvRank.setTextColor(0xFFF5CF1D);
//                }else {
//                    tvRank.setTextColor(getResources().getColor(R.color.text_color999999));
//                }
//                tvRank.setText("NO." + (position + 1));

//                holder.setTextView(R.id.tv_client, dateToString(mDataBeanList.get(position).getRdate()));
                if(position==0){
                    holder.setTextView(R.id.tv_rdate, "入账时间");
                    holder.setTextView(R.id.tv_inbill, "入账");
                    holder.setTextView(R.id.tv_outbill, "出账");
                    holder.setTextView(R.id.tv_otherinbill, "其他入账");
                    holder.setTextView(R.id.tv_otheroutbill, "其他入账");
//                    holder.setTextView(R.id.tv_balance, "余额");
                }else{
                    holder.setTextView(R.id.tv_rdate, ""+mDataBeanList.get(position).getRdate());
                    holder.setTextView(R.id.tv_inbill, ""+mDataBeanList.get(position).getInbill().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
                    holder.setTextView(R.id.tv_outbill, ""+mDataBeanList.get(position).getOutbill().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
                    holder.setTextView(R.id.tv_otherinbill, ""+mDataBeanList.get(position).getOtherinbill().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
                    holder.setTextView(R.id.tv_otheroutbill, ""+mDataBeanList.get(position).getOtheroutbill().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

                }
               //                holder.setTextView(R.id.tv_balance, ""+mDataBeanList.get(position).getBalance().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        };
        mRecyclerView.setAdapter(mAdapter);

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
        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        appDataBean.setAnalysistype(mAnalysistype+"");
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.BOSS_FUNDS_ANALYSIS, jsonData, FundsAnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        //关闭下拉
        smartRefreshLayout.finishRefresh();
        FundsAnalysisBean analysisBean = (FundsAnalysisBean) commonBean.getData();
        mTvUpdateTime.setText(analysisBean.getUpdatetime());
        DecimalFormat df = new DecimalFormat("0.00");
        mTvAmount.setText(df.format(analysisBean.getAmount()));//现有资金
        mDataBeanList.clear();
        mDataBeanList.addAll(analysisBean.getFunds_analysis());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
        //关闭下拉
        smartRefreshLayout.finishRefresh();
    }

    /**
     * 日期转字符串 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public  String dateToString(Date date) {

           ThreadLocal<SimpleDateFormat> sdfDay = new ThreadLocal<SimpleDateFormat>() {
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        String s = "";
        try {
            s = sdfDay.get().format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

}
