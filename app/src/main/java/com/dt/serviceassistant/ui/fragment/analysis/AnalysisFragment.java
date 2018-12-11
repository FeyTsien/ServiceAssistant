package com.dt.serviceassistant.ui.fragment.analysis;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AnalysisBean;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.CommonUtils;
import com.dt.serviceassistant.utils.DateUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 */

public class AnalysisFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View {
    private String TAG = getClass().getSimpleName();

    private List<String> mDataBeanList;

    private String mKeyword;
    private String mStartTime;
    private String mEndTime;
    private int mAnalysistype;
    private int mStart = 0;

    private View mRootView;
    private MyBaseAdapter mAdapter;

    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_analysis;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mDataBeanList = new ArrayList<>();
        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyBaseAdapter<String>(mDataBeanList, R.layout.item_analysis) {
            @Override
            public void bindView(MyBaseAdapter.MyViewHolder holder, int position) {
                if (position == 0) {
                    holder.getView(R.id.v_line1).setVisibility(View.INVISIBLE);
                } else if (position == (mDataBeanList.size() - 1)) {
                    holder.getView(R.id.v_line2).setVisibility(View.INVISIBLE);
                }
                holder.setTextView(R.id.tv_analysis, mDataBeanList.get(position));
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
                KeyboardUtils.hideSoftInput(getActivity());
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
        mPresenter.request(UrlUtils.BOSS_ANALYSIS_LIST, jsonData, AnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        AnalysisBean analysisBean = (AnalysisBean) commonBean.getData();
        if (mStart == 0) {
            mDataBeanList.clear();
        }
        mDataBeanList.addAll(analysisBean.getAnalysis());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        ToastUtils.showLong(msg);
    }

}
