package com.dt.serviceassistant.ui.activity.messagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPBaseActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageListAcitivity extends MVPBaseActivity<MessageListContract.View, MessgeListPresenter> implements MessageListContract.View, XRecyclerView.LoadingListener {

    public static final String MESSAGE_TYPE_NAME = "message_type_name";
    public static final String MESSAGE_TYPE = "message_type";

    private String mMessageTypeName;
    private int mMessageType;

    private List<MessageBean.DataBean> mDataBeanList;

    private MyBaseAdapter mAdapter;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.xrecyclerview)
    XRecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mMessageTypeName = getIntent().getStringExtra(MESSAGE_TYPE_NAME);
        mMessageType = getIntent().getIntExtra(MESSAGE_TYPE, 0);
        mTvTitle.setText(mMessageTypeName);
        mDataBeanList = new ArrayList<MessageBean.DataBean>();
        mPresenter.getMessageList(AppData.getUserId(), mMessageType);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//下拉刷新图片
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MessageBean.DataBean>(mDataBeanList, R.layout.item_message_l) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_message_time, mDataBeanList.get(position).getRtime());
                holder.setTextView(R.id.tv_message_content, mDataBeanList.get(position).getContent());
                if (position % 2 == 0) {
                    holder.setImageView(R.id.iv_mesage_pic, R.mipmap.tielu);
                }else if(position % 3 == 0){
                    holder.setImageView(R.id.iv_mesage_pic, R.mipmap.t1);
                } else {
                    holder.setImageView(R.id.iv_mesage_pic, R.mipmap.loli);
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void getMessageListSuccess(MessageBean messageBean) {
        mDataBeanList.clear();
        mDataBeanList.addAll(messageBean.getData());
        mRecyclerView.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMessageListFail(String error) {

    }

    @Override
    public void onRefresh() {
        if (!NetworkUtils.isConnected()) {
            mRecyclerView.refreshComplete();
            CommonUtils.showInfoDialog(this, "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        mPresenter.getMessageList(AppData.getUserId(), mMessageType);
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isConnected()) {
            mRecyclerView.refreshComplete();
            CommonUtils.showInfoDialog(this, "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        mRecyclerView.loadMoreComplete();
        mAdapter.notifyDataSetChanged();
    }
}
