package com.dt.serviceassistant.ui.fragment.message;//package com.dt.serviceassistant.ui.fragment.account;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.mywebview.WebViewActivity;
import com.dt.serviceassistant.ui.activity.messagelist.MessageListAcitivity;
import com.dt.serviceassistant.ui.adapter.MyAdapter;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.ui.fragment.information.InformationFragment;
import com.dt.serviceassistant.utils.CommonUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ft.widget.MultiItemDivider;


/**
 * ==============
 * ==== 消息 ====
 * ==============
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private List<MessageBean.DataBean.MsgBean> mDataBeanList;

    private View mRootView;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.xrv_message)
    XRecyclerView mRecyclerView;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, mRootView);
        initData();
        initView();
        return mRootView;
    }

    private void initData() {
        mDataBeanList = new ArrayList<MessageBean.DataBean.MsgBean>();
        onRefresh();
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
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//下拉刷新图片
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MessageBean.DataBean.MsgBean>(mDataBeanList, R.layout.item_message) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_message_title, mDataBeanList.get(position).getTypename());
                holder.setTextView(R.id.tv_message_time, mDataBeanList.get(position).getRtime());
                holder.setTextView(R.id.tv_content, mDataBeanList.get(position).getContent());
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        //item点击事件
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(MessageFragment.this.getActivity(), MessageListAcitivity.class);
                intent.putExtra(MessageListAcitivity.MESSAGE_TYPE_NAME, mDataBeanList.get(pos).getTypename());
                intent.putExtra(MessageListAcitivity.MESSAGE_TYPE, mDataBeanList.get(pos).getMessagetype());
                startActivity(intent);
            }
        });
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {//refresh data here
        mPresenter.getTpyeMessages(AppData.getUserId());
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        request();
    }

    private void request() {
        if (!NetworkUtils.isConnected()) {
            mRecyclerView.refreshComplete();
            CommonUtils.showInfoDialog(getActivity(), "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        mPresenter.getTpyeMessages(AppData.getUserId());
    }

    @Override
    public void getTpyeMessagesSuccess(MessageBean messageBean) {
        mDataBeanList.clear();
        mDataBeanList.addAll(messageBean.getData().getMsgX());
        mRecyclerView.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTpyeMessagesFail(String error) {
        mRecyclerView.refreshComplete();
    }
}
