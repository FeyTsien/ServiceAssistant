package com.dt.serviceassistant.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceInfo {
    private static DeviceInfo deviceInfo = null;
    public Context context;
    /**
     * 是否为debug模式
     */
    public boolean isDebug = false;
    public int width;
    public int height;

    public static DeviceInfo getInstance() {
        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo();
        }
        return deviceInfo;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void initDevice(boolean isDebug) {
        this.isDebug = isDebug;
        getDisplay();
    }

    /**
     * 获取分辨率
     *
     * @return
     */
    private String getDisplay() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;
        width = dm.widthPixels;
        String display = "";
        if (width < height) {
            display = dm.widthPixels + "*" + dm.heightPixels;
        } else {
            display = dm.heightPixels + "*" + dm.widthPixels;
        }
        return display;
    }

}
