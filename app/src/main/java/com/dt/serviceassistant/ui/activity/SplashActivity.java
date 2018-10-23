package com.dt.serviceassistant.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.ui.activity.login.LoginActivity;
import com.dt.serviceassistant.ui.activity.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SplashActivity extends BaseActivity {

    //ButterKnife是一个专注于Android系统的View注入框架,可以减少大量的findViewById以及setOnClickListener代码，可视化一键生成。
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private Unbinder bind;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //取消状态栏(已在style文件中设置了)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        LogUtils.Config config = ;
//        //logSwitch为false关闭日志
//        config.setLogSwitch(true);

        //绑定activity
        bind = ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 2000);

    }

    private void start() {
        // 如果是第一次启动，则先进入功能引导页
//        if (AppData.getFirstOpen()==1) {
//            CommonUtils.startActivity(this, WelcomeGuideActivity.class);
//            finish();
//        }else {
        ActivityUtils.startActivity(this, LoginActivity .class);
        finish();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
