package com.dt.serviceassistant.ui.activity.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MVPActivity;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.me.detail.InsuranceDetailAcitivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 发货列表
 */
public class InsuranceListAcitivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View, XRecyclerView.LoadingListener {

    private List<MBean.MsgBean> mDataBeanList;
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

    /**
     * 初始化数据
     */

    @Override
    protected void initData() {
        mDataBeanList = new ArrayList();
        onRefresh();
    }


    @Override
    protected void initView() {

        setToolBar(mToolbar, mTvTitle, "保险列表");
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

        mAdapter = new MyBaseAdapter<MBean.MsgBean>(mDataBeanList, R.layout.item_insurance) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_rtime, mDataBeanList.get(position).getItime());
                holder.setTextView(R.id.tv_rcompany, mDataBeanList.get(position).getRcompany());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        //item点击事件
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(InsuranceListAcitivity.this, InsuranceDetailAcitivity.class);
                intent.putExtra(InsuranceDetailAcitivity.INSURANCE_DATA_ITEM, mDataBeanList.get(pos));
                startActivity(intent);
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
        appDataBean.setStart(mStart+"");
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        if (TextUtils.equals(AppData.getRoleType(), "1")) {
            mPresenter.request(UrlUtils.GET_INSURANCE_LIST, jsonData, MBean.class);
        } else {
            mPresenter.request(UrlUtils.GET_STAFF_INSURANCE_LIST, jsonData, MBean.class);
        }
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        MBean mBean = (MBean) commonBean.getData();
        mRecyclerView.refreshComplete();
        if (mStart == 0) {
            mDataBeanList.clear();
        }
        mDataBeanList.addAll(mBean.getBiz());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        mRecyclerView.refreshComplete();
        ToastUtils.showLong(msg);
    }
}
