package com.dt.serviceassistant.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
/**
 * @author :   FeyTsien
 * @date :   2017/8/15
 */

public class MyApplication extends Application {

    private static Context mContext = null;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        System.out.println("获取context：etApplicationContext()");

        Utils.init(this);
//        LogUtils.getConfig()
//                .setLogSwitch(false)
//                .setConsoleSwitch(false);

        mContext = getApplicationContext();
    }

    /**
     * 解决java.lang.NoClassDefFoundError错误，方法数超过65536了
     * 5.0以下系统会出次问题
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
