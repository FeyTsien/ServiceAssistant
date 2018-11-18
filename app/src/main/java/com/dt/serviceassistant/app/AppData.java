package com.dt.serviceassistant.app;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dt.serviceassistant.bean.AppBean;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AppData {

    //是否是第一次打开APP
    private static final String KEY_IS_FIRST_OPEN = "is_first_open";
    //是否是新版APP
    private static final String KEY_IS_NEW_APP = "is_new_app";
    //是否登录
    private static final String KEY_IS_LOGIN_ED = "is_logined";
    //邮箱
    private static final String KEY_EMAIL = "email";
    //UserId
    private static final String KEY_USER_ID = "user_id";
    //手机号
    private static final String KEY_PHONE_NUMBER = "phone_number";
    //密码
    private static final String KEY_PASSWORD = "password";
    //角色类型
    private static final String KEY_ROLE_TYPE = "role_type";
    //Android设备Id
    private static final String KEY_ANDROID_ID = "android_id";


    /**
     * 引导页专用
     *
     * @param firstOpen
     */
    public static void setFirstOpen(int firstOpen) {
        SPUtils.getInstance().put(KEY_IS_FIRST_OPEN, firstOpen);
    }

    /**
     * 查看是否是第一次进入主界面（是否显示引导页）
     */
    public static int isFirstOpen() {
        return SPUtils.getInstance().getInt(KEY_IS_FIRST_OPEN, 1);
    }


    /**
     * 设置是否是最新版APP
     *
     * @param isNewApp
     */
    public static void setNewApp(boolean isNewApp) {
        SPUtils.getInstance().put(KEY_IS_NEW_APP, isNewApp);
    }

    /**
     * 查询是否是最新版APP
     *
     * @return
     */
    public static boolean isNewApp() {
        return SPUtils.getInstance().getBoolean(KEY_IS_NEW_APP, false);
    }


    /**
     * 设置是否登陆
     *
     * @param isLoginEd
     */
    public static void setLogined(boolean isLoginEd) {
        SPUtils.getInstance().put(KEY_IS_LOGIN_ED, isLoginEd);
    }

    /**
     * 查询是否登陆
     *
     * @return
     */
    public static boolean isLogined() {
        return SPUtils.getInstance().getBoolean(KEY_IS_LOGIN_ED, false);
    }

    /**
     * 保存UserId
     */
    public static void setUserId(String userId) {
        SPUtils.getInstance().put(KEY_USER_ID, userId);
    }


    /**
     * 获取UserId
     */
    public static String getUserId() {
        return SPUtils.getInstance().getString(KEY_USER_ID);
    }

    /**
     * 保存手机号
     */
    public static void setPhoneNumber(String phoneNumber) {
        SPUtils.getInstance().put(KEY_PHONE_NUMBER, phoneNumber);
    }


    /**
     * 获取手机号
     */
    public static String getPhoneNumber() {
        return SPUtils.getInstance().getString(KEY_PHONE_NUMBER);
    }

    /**
     * 保存密码
     */
    public static void setPassword(String password) {
        SPUtils.getInstance().put(KEY_PASSWORD, password);
    }

    /**
     * 获取密码
     */
    public static String getPassword() {
        return SPUtils.getInstance().getString(KEY_PASSWORD);
    }


    /**
     * 保存角色类型
     */
    public static void setRoleType(String roleType) {
        SPUtils.getInstance().put(KEY_ROLE_TYPE, roleType);
    }

    /**
     * 获取角色类型
     */
    public static String getRoleType() {
        return SPUtils.getInstance().getString(KEY_ROLE_TYPE);
    }

    /**
     * 保存手机设备ID
     */
    @SuppressLint("MissingPermission")
    public static void saveAndroidId() {
        SPUtils.getInstance().put(KEY_ANDROID_ID, PhoneUtils.getIMEI());
    }

    /**
     * 获取手机设备ID
     */
    public static String getAndroidId() {
        return SPUtils.getInstance().getString(KEY_ANDROID_ID);
    }

    /**
     * 清除登录信息
     */
    public static void clearLogin() {
        setPhoneNumber("");
        setPassword("");
        AppData.setLogined(false);
    }

    /**
     * 保存登录信息
     *
     * @param appBean
     */
    public static void saveLogin(AppBean appBean) {

        AppData.setLogined(true);
    }
}
