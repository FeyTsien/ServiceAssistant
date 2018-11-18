package com.dt.serviceassistant.ui.fragment.information;


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
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.bean.MessageBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.mywebview.WebTestActivity;
import com.dt.serviceassistant.mywebview.WebViewActivity;
import com.dt.serviceassistant.ui.activity.messagelist.MessageListAcitivity;
import com.dt.serviceassistant.ui.adapter.MyAdapter;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.ui.fragment.message.MessageFragment;
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
 * ================
 * ===== 资讯 =====
 * ================
 */

public class InformationFragment extends MVPBaseFragment<InformationContract.View, InformationPresenter> implements InformationContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private List<MessageBean.DataBean.MsgBean> mDataBeanList;
    private int mStart = 0;

    private View mRootView;
    //    private MyAdapter mAdapter;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.xrv_information)
    XRecyclerView mRecyclerView;

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_information, container, false);
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
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        //下拉刷新，上拉加载监听
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MessageBean.DataBean.MsgBean>(mDataBeanList, R.layout.item_information) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
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
                String url = mDataBeanList.get(pos).getUrl();
                if (url.startsWith("http")) {
                    WebViewActivity.loadUrl(getActivity(), url);
                } else {
                    WebViewActivity.loadUrl(getActivity(), "http://" + url);
                }

            }
        });

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mStart = 0;
        request();
    }

    /**
     * 上拉加载
     */
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
            CommonUtils.showInfoDialog(getActivity(), "网络不给力，请检查网络设置。", "提示", "知道了", null, null, null);
            return;
        }
        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        appDataBean.setStart(mStart);
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.getNews(UrlUtils.GET_NEWS_LIST, jsonData);
    }

    @Override
    public void getNewsSuccess(MessageBean messageBean) {

        mRecyclerView.refreshComplete();
        if (mStart == 0) {
            mDataBeanList.clear();
        }
        mDataBeanList.addAll(messageBean.getData().getMsgX());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNewsFail(String error) {

    }
}
