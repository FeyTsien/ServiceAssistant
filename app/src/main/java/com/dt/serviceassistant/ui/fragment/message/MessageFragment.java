package com.dt.serviceassistant.ui.fragment.message;//package com.dt.serviceassistant.ui.fragment.account;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.NetworkUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.messagelist.MessageListAcitivity;
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
 * ==============
 * ==== 消息 ====
 * ==============
 */

public class MessageFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private List<MessageBean.MsgBean> mDataBeanList;

    private View mRootView;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.xrv_message)
    XRecyclerView mRecyclerView;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        mDataBeanList = new ArrayList<MessageBean.MsgBean>();
        onRefresh();
    }

    @Override
    protected void initView() {
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

        mAdapter = new MyBaseAdapter<MessageBean.MsgBean>(mDataBeanList, R.layout.item_message) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_message_title, mDataBeanList.get(position).getTypename());
                holder.setTextView(R.id.tv_message_time, mDataBeanList.get(position).getRtime());
                holder.setTextView(R.id.tv_content, mDataBeanList.get(position).getContent());
                switch (mDataBeanList.get(position).getMessagetype() ){
                    case 0:
                        holder.setImageView(R.id.iv_mesage_pic, R.mipmap.icon_jigang );
                        break;
                    case 1:
                        holder.setImageView(R.id.iv_mesage_pic, R.mipmap.icon_qiyun);
                        break;
                    case 2:
                        holder.setImageView(R.id.iv_mesage_pic, R.mipmap.icon_chuanxun );
                        break;
                    case 3:
                        holder.setImageView(R.id.iv_mesage_pic, R.mipmap.icon_kaogang);
                        break;
                    case 4:
                        holder.setImageView(R.id.iv_mesage_pic, R.mipmap.icon_duizhang);
                        break;
                    case 5:
                        holder.setImageView(R.id.iv_mesage_pic, R.mipmap.icon_dingtian );
                        break;
                }

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
        request();
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

        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.GET_MESSAGE_TYPES, jsonData, MessageBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        MessageBean messageBean = (MessageBean) commonBean.getData();
        mDataBeanList.clear();
        mDataBeanList.addAll(messageBean.getBiz());
        mRecyclerView.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        mRecyclerView.refreshComplete();
    }
}
