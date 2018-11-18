package com.dt.serviceassistant.ui.activity.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseActivity;
import com.dt.serviceassistant.ui.activity.login.LoginActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ft.widget.MultiItemDivider;

/**
 * 发货列表
 */
public class TaskListAcitivity extends MVPBaseActivity<MeContract.View, MePresenter> implements MeContract.View, XRecyclerView.LoadingListener {

    private List<MBean.DataBean.MsgBean> mDataBeanList;
    private int mStart = 0;

    private MyBaseAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shipment_or_insurance_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDataBeanList = new ArrayList<MBean.DataBean.MsgBean>();
        onRefresh();
    }

    private void initView() {
        setToolBar(mToolbar, mTvTitle, "发货列表");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        MultiItemDivider itemDivider = new MultiItemDivider(this, MultiItemDivider.VERTICAL_LIST, R.drawable.divider_mileage);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        mRecyclerView.addItemDecoration(itemDivider);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        //下拉刷新，上拉加载监听
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MBean.DataBean.MsgBean>(mDataBeanList, R.layout.item_shipment) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_information_time, mDataBeanList.get(position).getRtime());
                holder.setTextView(R.id.tv_app, mDataBeanList.get(position).getNtitle());
                holder.setTextView(R.id.tv_information_title, mDataBeanList.get(position).getContent());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        //item点击事件
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //处理任务
                handleTask(mDataBeanList.get(pos).getId());
            }
        });

    }

    @Override
    public void onRefresh() {
        mStart = 0;
        request();
    }

    @Override
    public void onLoadMore() {
        mStart = mStart++;
        request();
    }

    /**
     * 请求保险列表
     */
    private void request() {
        if (!NetworkUtils.isConnected()) {
            mRecyclerView.refreshComplete();
            CommonUtils.showInfoDialog(this, "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        appDataBean.setStart(mStart);
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.GET_TASKS_LIST, jsonData);
    }

    private void handleTask(final int id) {

        if (!NetworkUtils.isConnected()) {
            mRecyclerView.refreshComplete();
            CommonUtils.showInfoDialog(this, "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }

        CommonUtils.showInfoDialog(this, "是否要处理该任务？", "提示", "取消", "处理", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppBean.DataBean appDataBean = new AppBean.DataBean();
                appDataBean.setUserid(AppData.getUserId());
                appDataBean.setId(id);
                Gson gson = new Gson();
                String jsonData = gson.toJson(appDataBean);
                mPresenter.request(UrlUtils.DO_WITH_TASK, jsonData);
            }
        });
    }

    @Override
    public void requestSuccess(MBean mBean, String requestUrl) {

        if (TextUtils.equals(requestUrl, UrlUtils.GET_TASKS_LIST)) {

            mRecyclerView.refreshComplete();
            if (mStart == 0) {
                mDataBeanList.clear();
            }
            mDataBeanList.addAll(mBean.getData().getMsgX());
            mAdapter.notifyDataSetChanged();
        } else if (TextUtils.equals(requestUrl, UrlUtils.DO_WITH_TASK)) {
            onRefresh();
        }
    }

    @Override
    public void requestFail(String msg) {
        mRecyclerView.refreshComplete();
        ToastUtils.showLong(msg);
    }

}
