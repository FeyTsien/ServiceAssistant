package com.dt.serviceassistant.ui.activity;

import android.content.Intent;
import android.widget.TextView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.mvp.MVPActivity;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPPresenter;

import butterknife.BindView;

public class InformationDetailActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_AUTHOR = "KEY_AUTHOR";
    public static final String KEY_DATE = "KEY_DATE";
    public static final String KEY_CONTENT = "KEY_CONTENT";


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ntitle)
    TextView tvNTitle;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        tvTitle.setText("资讯详情");
        tvNTitle.setText(intent.getStringExtra(KEY_TITLE));
        tvAuthor.setText(intent.getStringExtra(KEY_AUTHOR));
        tvDate.setText(intent.getStringExtra(KEY_DATE));
        tvContent.setText(intent.getStringExtra(KEY_CONTENT));
    }
}
