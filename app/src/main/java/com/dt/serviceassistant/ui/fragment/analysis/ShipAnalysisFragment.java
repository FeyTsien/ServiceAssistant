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
import com.dt.serviceassistant.bean.ShipAnalysisBean;
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


/**
 * 业务员分析
 */

public class ShipAnalysisFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View {
    private String TAG = getClass().getSimpleName();


    private List<ShipAnalysisBean.ShipSetInfoBean> mShipSetInfoList;
    private List<ShipAnalysisBean.ConfigurationInfoBean> mConfigurationInfoList;
    private List<ShipAnalysisBean.ShippingInfoBean> mShippingInfoList;

    private MyBaseAdapter mAdapter1;
    private MyBaseAdapter mAdapter2;
    private MyBaseAdapter mAdapter3;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view_ship_set)
    RecyclerView mRecyclerViewShipSet;
    @BindView(R.id.recycler_view_configuration)
    RecyclerView mRecyclerViewConfiguration;
    @BindView(R.id.recycler_view_shipping)
    RecyclerView mRecyclerViewShipping;

    public static ShipAnalysisFragment newInstance() {
        return new ShipAnalysisFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ship_analysis;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mShipSetInfoList = new ArrayList<>();
        mConfigurationInfoList = new ArrayList<>();
        mShippingInfoList = new ArrayList<>();
    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShipSet.setLayoutManager(layoutManager);
        mRecyclerViewConfiguration.setLayoutManager(layoutManager2);
        mRecyclerViewShipping.setLayoutManager(layoutManager3);

        mAdapter1 = new MyBaseAdapter<ShipAnalysisBean.ShipSetInfoBean>(mShipSetInfoList, R.layout.item_ship_analysis) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvLocation = holder.getView(R.id.tv_location);
                TextView tv1 = holder.getView(R.id.tv1);
                TextView tv2 = holder.getView(R.id.tv2);
                TextView tv3 = holder.getView(R.id.tv3);
                TextView tv4 = holder.getView(R.id.tv4);

                tvName.setText(mShipSetInfoList.get(position).getName());
                tvLocation.setText(mShipSetInfoList.get(position).getLocation());
                tv1.setText("合同签订："+mShipSetInfoList.get(position).getSign_date());
                tv2.setText("预计到港："+mShipSetInfoList.get(position).getArrival_date());
                tv3.setText("核定载重："+mShipSetInfoList.get(position).getWeight());
                tv4.setText("合同单价："+mShipSetInfoList.get(position).getUnit_price());
            }
        };
        mAdapter2 = new MyBaseAdapter<ShipAnalysisBean.ConfigurationInfoBean>(mConfigurationInfoList, R.layout.item_ship_analysis) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvLocation = holder.getView(R.id.tv_location);
                TextView tv1 = holder.getView(R.id.tv1);
                TextView tv2 = holder.getView(R.id.tv2);
                TextView tv3 = holder.getView(R.id.tv3);
                TextView tv4 = holder.getView(R.id.tv4);

                tvName.setText(mConfigurationInfoList.get(position).getName());
                tvLocation.setText(mConfigurationInfoList.get(position).getLocation());
                tv1.setText("合同签订："+mConfigurationInfoList.get(position).getSign_date());
                tv2.setText("陪在日期："+mConfigurationInfoList.get(position).getStowage_date());
                tv3.setText("核定载重："+mConfigurationInfoList.get(position).getWeight());
                tv4.setText("配置吨位："+mConfigurationInfoList.get(position).getConfiguration_tonnage());
            }
        };
        mAdapter3 = new MyBaseAdapter<ShipAnalysisBean.ShippingInfoBean>(mShippingInfoList, R.layout.item_ship_analysis) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvLocation = holder.getView(R.id.tv_location);
                TextView tv1 = holder.getView(R.id.tv1);
                TextView tv2 = holder.getView(R.id.tv2);
                TextView tv3 = holder.getView(R.id.tv3);
                TextView tv4 = holder.getView(R.id.tv4);

                tvName.setText(mShippingInfoList.get(position).getName());
                tvLocation.setText(mShippingInfoList.get(position).getLocation());
                tv1.setText("合同签订："+mShippingInfoList.get(position).getSign_date());
                tv2.setText("起运日期："+mShippingInfoList.get(position).getShipping_date());
                tv3.setText("核定载重："+mShippingInfoList.get(position).getWeight());
                tv4.setText("配置吨位："+mShippingInfoList.get(position).getConfiguration_tonnage());
            }
        };

        mRecyclerViewShipSet.setAdapter(mAdapter1);
        mRecyclerViewConfiguration.setAdapter(mAdapter2);
        mRecyclerViewShipping.setAdapter(mAdapter3);

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
        mPresenter.request("5cuWIJl6", "", ShipAnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        //关闭下拉
        smartRefreshLayout.finishRefresh();

        ShipAnalysisBean shipAnalysisBean = (ShipAnalysisBean) commonBean.getData();

        mShipSetInfoList.clear();
        mShipSetInfoList.addAll(shipAnalysisBean.getShip_set_info());
        mConfigurationInfoList.clear();
        mConfigurationInfoList.addAll(shipAnalysisBean.getConfiguration_info());
        mShippingInfoList.clear();
        mShippingInfoList.addAll(shipAnalysisBean.getShipping_info());

        mAdapter1.notifyDataSetChanged();
        mAdapter2.notifyDataSetChanged();
        mAdapter3.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
        //关闭下拉
        smartRefreshLayout.finishRefresh();
        ToastUtils.showLong(msg);
    }

}