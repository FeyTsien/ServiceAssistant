package com.dt.serviceassistant.ui.fragment.information;


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
import com.dt.serviceassistant.mywebview.WebViewActivity;
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
 * ================
 * ===== 资讯 =====
 * ================
 */

public class InformationFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private List<MessageBean.MsgBean> mDataBeanList;
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
    protected int getLayoutId() {
        return R.layout.fragment_information;
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
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        //下拉刷新，上拉加载监听
        mRecyclerView.setLoadingListener(this);

        mAdapter = new MyBaseAdapter<MessageBean.MsgBean>(mDataBeanList, R.layout.item_information) {
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
        mPresenter.request(UrlUtils.GET_NEWS_LIST, jsonData,MessageBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        MessageBean messageBean = (MessageBean) commonBean.getData();
        if (mStart == 0) {
            mDataBeanList.clear();
        }
        mDataBeanList.addAll(messageBean.getBiz());
        mRecyclerView.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        mRecyclerView.refreshComplete();
    }
}
