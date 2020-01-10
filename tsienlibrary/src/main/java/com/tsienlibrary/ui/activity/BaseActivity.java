package com.tsienlibrary.ui.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.tsienlibrary.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fey Tesin on 2017/07/05.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private String TAG = "BaseActivity";
    protected Unbinder unbinder;

    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();
    public static BaseActivity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置底部导航栏颜色
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        synchronized (mActivities) {
            mActivities.add(this);
        }

        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        //设置沉浸式状态栏
        setStstus(false,false);
        initPresenter();
        initData();
        initView(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * TODO:界面布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     *
     * TODO：设置沉浸式状态栏
     * @param isWhite true = 白色。
     */
    protected void setStstus(boolean isfitsSystemWindows,boolean isWhite) {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, isfitsSystemWindows);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        if (!isWhite) {
            //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
            //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(this, 0x55000000);
            }
        }
    }

    /**
     * TODO:初始化共用Presenter
     */
    protected void initPresenter() {
    }

    /**
     * TODO:初始化数据
     */
    protected abstract void initData();

    /**
     * TODO:初始化View
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * TODO:是否订阅事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        //当应用在后台运行超过30秒（默认）再回到前端，将被认为是两个独立的session(启动)，
        // 例如用户回到home，或进入其他程序，经过一段时间后再返回之前的应用。可通过接口：
        //MobclickAgent.setSessionContinueMillis( 60000); //来自定义这个间隔（参数单位为毫秒）。
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }


    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, TextView textView, CharSequence title) {
        setToolBar(toolbar, textView, title, true);
    }

    /**
     * 子类可以直接用
     *
     * @param resid
     */
    protected void setToolBar(Toolbar toolbar, TextView textView, int resid) {
        CharSequence title = getResources().getText(resid);
        setToolBar(toolbar, textView, title, true);
    }

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, TextView textView, CharSequence title, boolean enable) {
        textView.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//不显示toolbar的标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     */
    /**
     * 子类可以直接用（暂时没有使用）
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        setToolBar(toolbar, title, true);
    }

    /**
     * 子类可以直接用（暂时没有使用）
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title, boolean enable) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(enable);//显示toolbar的标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public String versionName;
    public int versioncode;

    /**
     * 返回当前程序版本名
     */
    public void getAppVersionName(Context context) {
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;

        } catch (Exception e) {
            LogUtils.e("VersionInfo", "Exception", e);
        }

    }

    public void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
