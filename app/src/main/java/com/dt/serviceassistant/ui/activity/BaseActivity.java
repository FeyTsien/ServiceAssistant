package com.dt.serviceassistant.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dt.serviceassistant.app.ACache;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fey Tesin on 2017/07/05.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private String TAG = "BaseActivity";
    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();

    //轻量级的数据缓存工具类
    public ACache mCache;
    public static BaseActivity activity;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mAvatarSize;
    protected int mWidth;
    protected int mHeight;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置没有系统自带标题头
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置为竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mCache = ACache.get(this);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        getDM();
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

    protected void getDM(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mAvatarSize = (int) (50 * mDensity);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

}
