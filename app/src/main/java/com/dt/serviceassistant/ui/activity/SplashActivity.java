package com.dt.serviceassistant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.ui.activity.login.LoginPwdActivity;
import com.dt.serviceassistant.ui.activity.main.MainActivity;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SplashActivity extends AppCompatActivity {

    //ButterKnife是一个专注于Android系统的View注入框架,可以减少大量的findViewById以及setOnClickListener代码，可视化一键生成。
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
        Intent intent;
        if (AppData.isLogined()) {
            if (TextUtils.equals(AppData.getRoleType(), "1") || TextUtils.equals(AppData.getRoleType(), "3")) {
                intent = new Intent(this, MainActivity.class);
            } else if (TextUtils.equals(AppData.getRoleType(), "2")) {
                intent = new Intent(this, MainBossActivity.class);
            } else {
                intent = new Intent(this, LoginPwdActivity.class);
            }
        } else {
            intent = new Intent(this, LoginPwdActivity.class);
        }
        startActivity(intent);
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
