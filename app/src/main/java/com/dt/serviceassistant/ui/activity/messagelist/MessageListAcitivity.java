package com.dt.serviceassistant.ui.activity.messagelist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MVPActivity;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageListAcitivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View, XRecyclerView.LoadingListener {

    public static final String MESSAGE_TYPE_NAME = "message_type_name";
    public static final String MESSAGE_TYPE = "message_type";

    private String mMessageTypeName;
    private int mMessageType;

    private List<MessageBean.MsgBean> mDataBeanList;
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
        return R.layout.activity_message_list;
    }

    @Override
    protected void initData() {
        mMessageTypeName = getIntent().getStringExtra(MESSAGE_TYPE_NAME);
        mMessageType = getIntent().getIntExtra(MESSAGE_TYPE, 0);
        mDataBeanList = new ArrayList<MessageBean.MsgBean>();

        onRefresh();
    }


    @Override
    protected void initView() {


        setToolBar(mToolbar, mTvTitle, mMessageTypeName);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);//下拉刷新图片
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MessageBean.MsgBean>(mDataBeanList, R.layout.item_message_l) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_message_time, mDataBeanList.get(position).getRtime());
                holder.setTextView(R.id.tv_message_content, mDataBeanList.get(position).getContent());
            }
        };
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        MessageBean msgBean = (MessageBean) commonBean.getData();
        mDataBeanList.clear();
        mDataBeanList.addAll(msgBean.getBiz());
        mRecyclerView.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        mRecyclerView.refreshComplete();
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
        appDataBean.setMessagetype(mMessageType+"");
        appDataBean.setStart(mStart+"");
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.GET_MESSAGE_LIST, jsonData, MessageBean.class);

    }
}
