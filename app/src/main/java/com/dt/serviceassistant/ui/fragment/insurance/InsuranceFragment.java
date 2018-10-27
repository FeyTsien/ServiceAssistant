package com.dt.serviceassistant.ui.fragment.insurance;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.NetworkUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.ui.activity.insurancedetail.InsuranceDetailAcitivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ft.widget.MultiItemDivider;

/**
 * ================
 * ===== 保险 =====
 * ================
 */

public class InsuranceFragment extends MVPBaseFragment<InsuranceContract.View, InsurancePresenter> implements InsuranceContract.View, XRecyclerView.LoadingListener {

    private String TAG = getClass().getSimpleName();

    private List<MBean.DataBean> mDataBeanList;

    private View mRootView;
    //    private MyAdapter mAdapter;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;

    public static InsuranceFragment newInstance() {
        return new InsuranceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_insurance, container, false);
        ButterKnife.bind(this, mRootView);
        initData();
        initView();
        return mRootView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDataBeanList = new ArrayList<MBean.DataBean>();
        mPresenter.getInsuranceList(AppData.getUserId());
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_mileage);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        mRecyclerView.addItemDecoration(itemDivider);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        //下拉刷新，上拉加载监听
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MBean.DataBean>(mDataBeanList, R.layout.item_insurance) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_description, mDataBeanList.get(position).getDescription());
                holder.setTextView(R.id.tv_rcompany, mDataBeanList.get(position).getRcompany());
                holder.setTextView(R.id.tv_information_time, mDataBeanList.get(position).getRtime());
                holder.setTextView(R.id.tv_status, mDataBeanList.get(position).getStatus());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        //item点击事件
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(InsuranceFragment.this.getActivity(), InsuranceDetailAcitivity.class);
                intent.putExtra(InsuranceDetailAcitivity.INSURANCE_DATA_ITEM, mDataBeanList.get(pos));
                startActivity(intent);
            }
        });

    }


    @Override
    public void getInsuranceListSuccess(MBean mBean) {
        mDataBeanList.clear();
        mDataBeanList.addAll(mBean.getData());
        mRecyclerView.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getInsuranceListFail(String msg) {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {//refresh data here
        if (!NetworkUtils.isConnected()) {
            mRecyclerView.refreshComplete();
            CommonUtils.showInfoDialog(getActivity(), "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        mPresenter.getInsuranceList(AppData.getUserId());
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        mRecyclerView.loadMoreComplete();
        mAdapter.notifyDataSetChanged();
    }

}
