package com.dt.serviceassistant.ui.fragment.analysis;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.MBean;
import com.dt.serviceassistant.mvp.MContract;
import com.dt.serviceassistant.mvp.MPresenter;
import com.dt.serviceassistant.mvp.MVPBaseFragment;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.DateUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ft.widget.MultiItemDivider;


/**
 */

public class AnalysisFragment extends MVPBaseFragment<MContract.View, MPresenter> implements MContract.View, XRecyclerView.LoadingListener {
    private String TAG = getClass().getSimpleName();

    private List<MBean.DataBean> mDataBeanList;

    private String mKeyword;
    private String mStartTime;
    private String mEndTime;
    private int mAnalysistype;
    private int mStart;

    private View mRootView;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.recycler_view)
    XRecyclerView mRecyclerView;

    public static AnalysisFragment newInstance() {
        return new AnalysisFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        ButterKnife.bind(this, mRootView);
        initData();
        initView();
        return mRootView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDataBeanList = new ArrayList<>();
        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
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

        mAdapter = new MyBaseAdapter<MBean.DataBean>(mDataBeanList, R.layout.item_message) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
//                holder.setTextView(R.id.tv_message_title, mDataBeanList.get(position).getTypename());
//                holder.setTextView(R.id.tv_message_time, mDataBeanList.get(position).getRtime());
//                holder.setTextView(R.id.tv_content, mDataBeanList.get(position).getContent());
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        //item点击事件
        mAdapter.setOnItemClickListener(new MyBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
            }
        });
    }


    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.btn_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                DateUtils.showDatePickerDialog(getActivity(), 0, mTvStartTime, Calendar.getInstance());
                break;
            case R.id.tv_end_time:
                DateUtils.showDatePickerDialog(getActivity(), 0, mTvEndTime, Calendar.getInstance());
                break;
            case R.id.btn_query:
                request();
                break;
        }
    }

    /**
     * 请求
     */
    private void request() {
        mKeyword = mEtKeyword.getText().toString();
        mStartTime = mTvStartTime.getText().toString();
        mEndTime = mTvEndTime.getText().toString();

        AppBean.DataBean appDataBean = new AppBean.DataBean();
        appDataBean.setUserid(AppData.getUserId());
        appDataBean.setKeyword(mKeyword);
        appDataBean.setStarttime(mStartTime);
        appDataBean.setEndtime(mEndTime);
        appDataBean.setAnalysistype(mAnalysistype);
        appDataBean.setStart(mStart);
        Gson gson = new Gson();
        String jsonData = gson.toJson(appDataBean);
        mPresenter.request(UrlUtils.BOSS_ANALYSIS_LIST, jsonData);
    }

    @Override
    public void requestSuccess(MBean mBean) {
        mRecyclerView.refreshComplete();
        mDataBeanList.clear();
        mDataBeanList.addAll(mBean.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String msg) {
        mRecyclerView.refreshComplete();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
