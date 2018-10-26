package com.dt.serviceassistant.ui.fragment.shipments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.mywebview.WebViewActivity;
import com.dt.serviceassistant.ui.activity.shipmentinfo.ShipmentInfoAcitivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ft.widget.MultiItemDivider;


/**
 * ================
 * ===== 发货 =====
 * ================
 */

public class ShipmentsFragment extends MVPBaseFragment<ShipmentsContract.View, ShipmentsPresenter> implements ShipmentsContract.View, XRecyclerView.LoadingListener {

    private String TAG = getClass().getSimpleName();

    private ArrayList<String> listData;

    private View mRootView;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.xrv_shipments)
    XRecyclerView mRecyclerView;


    public static ShipmentsFragment newInstance() {
        return new ShipmentsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_shipments, container, false);
        ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
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
        initData();   //初始化数据

        mAdapter = new MyBaseAdapter<String>(listData, R.layout.item_shipment) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
//                holder.setTextView(R.id.tv_information_title, listData.get(position));

            }
        };
        mRecyclerView.setAdapter(mAdapter);

        //item点击事件
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ActivityUtils.startActivity(new Intent(ShipmentsFragment.this.getActivity(),ShipmentInfoAcitivity.class));
            }
        });

        //下拉刷新，上拉加载监听
        mRecyclerView.setLoadingListener(this);

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

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {//refresh data here
        new Handler().postDelayed(new Runnable() {
            public void run() {
                listData.clear();
                for (int i = 0; i < 15; i++) {
                    listData.add("item" + i + "after times of refresh");
                }
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
                ToastUtils.showLong("已刷新");
            }

        }, 1000);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mRecyclerView.loadMoreComplete();
                for (int i = 0; i < 15; i++) {
                    listData.add("item" + (i + listData.size()));
                }
                mRecyclerView.loadMoreComplete();
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override
    public void getShipmentListSuccess(MessageBean messageBean) {

    }

    @Override
    public void getShipmentListFail(String error) {

    }
}
